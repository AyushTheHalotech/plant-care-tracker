package com.thehalotech.planthealthtracker.presentation.plantlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thehalotech.planthealthtracker.domain.repository.PlantRepository
import com.thehalotech.planthealthtracker.domain.usecase.GetPlantsUseCase
import com.thehalotech.planthealthtracker.domain.usecase.MarkPlantWateredUseCase

class PlantListViewModelFactory(
    private val getPlantsUseCase: GetPlantsUseCase,
    private val markPlantWatered: MarkPlantWateredUseCase,
) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantListViewModel::class.java)) {
            return PlantListViewModel(getPlantsUseCase, markPlantWatered) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}