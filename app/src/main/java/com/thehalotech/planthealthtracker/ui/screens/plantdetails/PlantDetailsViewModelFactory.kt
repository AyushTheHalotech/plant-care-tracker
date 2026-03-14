package com.thehalotech.planthealthtracker.ui.screens.plantdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thehalotech.planthealthtracker.data.repository.PlantRepository

class PlantDetailsViewModelFactory(
    private val plantRepository: PlantRepository,
    private val plantName: String
) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantDetailsViewModel::class.java)) {
            return PlantDetailsViewModel(plantRepository, plantName) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}