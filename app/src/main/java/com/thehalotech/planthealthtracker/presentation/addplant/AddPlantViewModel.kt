package com.thehalotech.planthealthtracker.presentation.addplant

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thehalotech.planthealthtracker.data.api.PlantApiService
import com.thehalotech.planthealthtracker.data.local.PlantEntity
import com.thehalotech.planthealthtracker.domain.model.IdentifiedPlantDomain
import com.thehalotech.planthealthtracker.domain.model.Plant
import com.thehalotech.planthealthtracker.domain.repository.PlantRepository
import com.thehalotech.planthealthtracker.domain.usecase.AddPlantUseCase
import com.thehalotech.planthealthtracker.domain.usecase.GetPlantDetailsUseCase
import com.thehalotech.planthealthtracker.domain.usecase.SearchPlantsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

@OptIn(FlowPreview::class)
class AddPlantViewModel(
    private val searchPlants: SearchPlantsUseCase,
    private val getPlantDetails: GetPlantDetailsUseCase,
    private val addPlants: AddPlantUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "TheHalotech::AddPlantViewModel"
    }

    var searchQuery by mutableStateOf("")
        private set

    var searchResults by mutableStateOf<List<IdentifiedPlantDomain>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var selectedPlant: IdentifiedPlantDomain? by mutableStateOf<IdentifiedPlantDomain?>(null)
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

    var plantId by mutableStateOf("")
        private set

    var selectedImageUri by mutableStateOf<Uri?>(null)
        private set

    private val queryFlow = MutableStateFlow("")


    init {
        viewModelScope.launch {
            queryFlow
                .debounce(300)
                .filter { it.length > 2 }
                .distinctUntilChanged()
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
            searchResults = searchPlants.invoke(query)
            Log.i(TAG, searchResults.toString())
        } catch (e: Exception) {
            searchResults = emptyList()
        }
        isLoading = false
    }

    fun onPlantSelected(plant: IdentifiedPlantDomain) {
        selectedPlant = plant
        if(null != selectedPlant) {
            Log.i(TAG, plant.toString())
            searchQuery = plant.entityName
            plantId = plant.accessToken
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
        isLoading = true

        try{
            val details = getPlantDetails(plantId)
            plantName = details.name
            plantType = details.commonName
            wateringSchedule = details.wateringFreq.toString()
            lightRequirement = details.lightRequirement
            description = details.description

        } catch (e: Exception) {
            Log.e(TAG, e.printStackTrace().toString())

        }

        isLoading = false
    }

    fun addPlant(onFinished: () -> Unit) {
        val dateAdded = LocalDate.now().toEpochDay()
        viewModelScope.launch {
            val plant = Plant(
                name = plantName,
                commonName = plantType,
                wateringFrequency = wateringSchedule.toInt(),
                lastWatered = dateAdded,
                lightRequirement = lightRequirement,
                description = description,
                id = plantId
            )
            addPlants(plant, selectedImageUri)
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

