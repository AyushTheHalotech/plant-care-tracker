package com.thehalotech.planthealthtracker.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thehalotech.planthealthtracker.data.api.PlantApiService
import com.thehalotech.planthealthtracker.data.repository.PlantRepository

class DashboardViewModelFactory(
    private val plantRepository: PlantRepository
) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(plantRepository) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}