package com.thehalotech.planthealthtracker.presentation.plantlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thehalotech.planthealthtracker.domain.usecase.GetPlantsUseCase
import com.thehalotech.planthealthtracker.domain.usecase.MarkPlantWateredUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PlantListViewModel(
    private val getPlantsUseCase: GetPlantsUseCase,
    private val markPlantWatered: MarkPlantWateredUseCase,
) : ViewModel() {

    val plants = getPlantsUseCase().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun markWatered(plantId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            markWatered(plantId)
        }
    }
    
}