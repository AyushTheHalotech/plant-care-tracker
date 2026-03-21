package com.thehalotech.planthealthtracker.presentation.plantdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thehalotech.planthealthtracker.domain.repository.PlantRepository
import com.thehalotech.planthealthtracker.domain.usecase.GetPlantByIdUseCase
import com.thehalotech.planthealthtracker.domain.usecase.MarkPlantWateredUseCase

class PlantDetailsViewModelFactory(
    private val getPlantById: GetPlantByIdUseCase,
    private val markPlantWatered: MarkPlantWateredUseCase,
    private val plantId: String
) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantDetailsViewModel::class.java)) {
            return PlantDetailsViewModel(getPlantById, markPlantWatered, plantId) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}