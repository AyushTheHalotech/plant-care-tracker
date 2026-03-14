package com.thehalotech.planthealthtracker.ui.screens.plantlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thehalotech.planthealthtracker.data.api.PlantApiService
import com.thehalotech.planthealthtracker.data.repository.PlantRepository

class PlantListViewModelFactory(
    private val plantRepository: PlantRepository
) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantlistViewModel::class.java)) {
            return PlantlistViewModel(plantRepository) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}