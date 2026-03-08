package com.thehalotech.planthealthtracker.ui.screens.addplants

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.thehalotech.planthealthtracker.BuildConfig
import com.thehalotech.planthealthtracker.data.api.PlantApiService
import com.thehalotech.planthealthtracker.data.api.RetrofitClient
import com.thehalotech.planthealthtracker.data.model.PlantDetails
import com.thehalotech.planthealthtracker.data.model.PlantSearchItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(FlowPreview::class)
class AddPlantViewModel(private val api: PlantApiService) : ViewModel() {

    companion object {
        private const val TAG = "TheHalotech::AddPlantViewModel"
    }

    var searchQuery by mutableStateOf("")
        private set

    var searchResults by mutableStateOf<List< PlantSearchItem>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var selectedPlant by mutableStateOf<PlantSearchItem?>(null)
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

    private val queryFlow = MutableStateFlow("")

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
            val response = api.searchPlants(BuildConfig.PLANT_API_KEY, query)
            searchResults = response.data
                .take(4)
            Log.i(TAG, response.toString())
        } catch (e: Exception) {
            searchResults = emptyList()
        }
        isLoading = false
    }

    fun onPlantSelected(plant: PlantSearchItem) {
        selectedPlant = plant
        Log.i(TAG, plant.toString())
        searchQuery = plant.commonName ?: ""
        searchResults = emptyList()
        viewModelScope.launch {
            fetchPlantDetails(plant.id)
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

    private suspend fun fetchPlantDetails(plantId: Int) {
        if(plantId > 3000) {
            Log.i(TAG, "Plant ID is greater than 3000, upgrade to premium plan")
            return
        }
        try {
            isLoading = true
            Log.i(TAG, "Fetching plant details")
            val response = api.getPlantDetailsRaw(
                plantId,
                BuildConfig.PLANT_API_KEY
            )
            Log.i(TAG, "HTTP code: ${response.code()}")
            Log.i(TAG, "HTTP message: ${response.message()}")
            Log.i(TAG, "Response headers: ${response.headers()}")

            if(response.isSuccessful) {
                val body = response.body()
                Log.i(TAG, "Response body: $body")
                if (body != null && body.trim().startsWith("{")) {
                    val detailsResonse = Gson().fromJson(body, PlantDetails::class.java)
                    val wateringFrequency = detailsResonse.wateringBenchmark?.value
                        ?.replace("\"", "")
                        ?.split("-")
                        ?.firstOrNull()
                        ?: "0"
                    Log.i(TAG, wateringFrequency)
                    val sunlightRequirements = detailsResonse.sunlight?.joinToString(";") ?: ""

                    withContext(Dispatchers.Main) {
                        plantName = detailsResonse.commonName ?: ""
                        plantType = detailsResonse.scientificName?.firstOrNull() ?: ""
                        wateringSchedule = "$wateringFrequency days"
                        lightRequirement = sunlightRequirements
                        description = detailsResonse.description ?: ""
                    }

                } else {
                    Log.e(TAG, "Invalid response format")
                }
            }


        } catch (e: Exception) {
            Log.e(TAG, e.printStackTrace().toString())
        }
        isLoading = false
    }

}