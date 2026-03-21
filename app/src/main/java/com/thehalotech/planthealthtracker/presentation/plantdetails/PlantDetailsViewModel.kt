package com.thehalotech.planthealthtracker.presentation.plantdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thehalotech.planthealthtracker.domain.repository.PlantRepository
import com.thehalotech.planthealthtracker.domain.usecase.GetPlantByIdUseCase
import com.thehalotech.planthealthtracker.domain.usecase.MarkPlantWateredUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PlantDetailsViewModel(
    private val getPlantById: GetPlantByIdUseCase,
    private val markPlantWatered: MarkPlantWateredUseCase,
    private val plantId: String
) : ViewModel() {

    val plant = getPlantById(plantId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)


    fun onWateredClicked(plantId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            markPlantWatered(plantId)
        }
    }

}