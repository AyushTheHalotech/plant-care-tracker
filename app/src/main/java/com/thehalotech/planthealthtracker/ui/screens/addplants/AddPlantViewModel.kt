package com.thehalotech.planthealthtracker.ui.screens.addplants

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thehalotech.planthealthtracker.data.api.PlantApiService
import com.thehalotech.planthealthtracker.data.local.MyPlantsTable
import com.thehalotech.planthealthtracker.data.model.PlantEntity
import com.thehalotech.planthealthtracker.data.repository.PlantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

@OptIn(FlowPreview::class)
class AddPlantViewModel(private val api: PlantApiService,
    private val repository: PlantRepository) : ViewModel() {

    companion object {
        private const val TAG = "TheHalotech::AddPlantViewModel"
    }

    var searchQuery by mutableStateOf("")
        private set

    var searchResults by mutableStateOf<List< PlantEntity>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var selectedPlant: PlantEntity? by mutableStateOf<PlantEntity?>(null)
        private set

    var plantName by mutableStateOf("")
        private set

    var plantType by mutableStateOf("")
        private set

    var wateringSchedule by mutableStateOf("")
        private set

    var lightRequirement by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var selectedImageUri by mutableStateOf<Uri?>(null)
        private set

    private val queryFlow = MutableStateFlow("")

    val plants = repository.plants


    init {
        viewModelScope.launch {
            queryFlow
                .debounce(1000)
                .filter { it.length > 2 }
                .collectLatest { query ->
                    searchPlant(query)
                }
        }
    }

    fun onQueryChange(query: String) {
        searchQuery = query
        queryFlow.value = query
    }

    private suspend fun searchPlant(query: String) {
        try {
            isLoading = true
            val response = api.searchPlantsName(query)
            searchResults = response.entities
                .take(4)
            Log.i(TAG, response.toString())
        } catch (e: Exception) {
            searchResults = emptyList()
        }
        isLoading = false
    }

    fun onPlantSelected(plant: PlantEntity) {
        selectedPlant = plant
        if(null != selectedPlant) {
            Log.i(TAG, plant.toString())
            searchQuery = plant.entityName ?: ""
            searchResults = emptyList()
        } else {
            searchResults = emptyList()
        }


        viewModelScope.launch {
            fetchPlantDetails(plant.accessToken)
        }

    }

    fun updatePlantType(value: String) {
        plantType = value
    }

    fun updateWateringRequirement(value: String) {
        wateringSchedule = value
    }

    fun updateLightRequirement(value: String) {
        lightRequirement = value
    }

    fun updateDescription(value: String) {
        description = value
    }

    fun updatePlantName(value: String) {
        plantName = value
    }

    val details = "common_names,description,watering,best_light_condition"

    private suspend fun fetchPlantDetails(plantId: String) {
        Log.i(TAG, "Fetching plant details")
        try {
            isLoading = true
            val response = api.getPlantDetailsByName(
                plantId,
                details
            )
            Log.i(TAG, response.toString())
            val common_name = response.commonNames.firstOrNull()?:""
            val det = response.description.value
            val wateringObj = response.watering
            var moisture = 2
            if(wateringObj != null) {
                moisture = response.watering.min
            }
            var wateringFreq = 0
            val sunlight = response.bestLightCondition
            val name = response.name


            wateringFreq = when(moisture) {
                1 -> 7
                2 -> 3
                3 -> 1
                else -> 2
            }

            withContext(Dispatchers.Main) {
                plantName = name
                plantType = common_name
                wateringSchedule = wateringFreq.toString()
                lightRequirement = sunlight
                description = det
            }

        } catch (e: Exception) {
            Log.e(TAG, e.printStackTrace().toString())
        }
        isLoading = false
    }

    fun addPlant(onFinished: () -> Unit) {
        val dateAdded = LocalDate.now().toEpochDay()
        viewModelScope.launch {
            val plant = MyPlantsTable(
                plantName = plantName,
                commonName = plantType,
                wateringFrequency = wateringSchedule,
                lastWatered = dateAdded,
                lightRequirement = lightRequirement,
                description = description
            )
            repository.addPlant(plant, selectedImageUri)
            resetForm()
            onFinished()
        }
    }

    fun updateSelectedImageUri(uri: Uri?) {
        selectedImageUri = uri
    }

    fun resetForm() {
        searchQuery = ""
        searchResults = emptyList()

        selectedPlant = null
        plantName = ""

        plantType = ""
        wateringSchedule = ""
        lightRequirement = ""
        description = ""

        selectedImageUri = null
    }
}

