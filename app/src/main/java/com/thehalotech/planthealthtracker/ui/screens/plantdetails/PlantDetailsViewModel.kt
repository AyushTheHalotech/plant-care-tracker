package com.thehalotech.planthealthtracker.ui.screens.plantdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thehalotech.planthealthtracker.data.repository.PlantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class PlantDetailsViewModel(
    private val repository: PlantRepository,
    private val plantName: String
) : ViewModel() {

    val plant = repository.getPlant(plantName)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)


    fun markPlantWatered(plantName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.markWatered(plantName)
        }
    }

}