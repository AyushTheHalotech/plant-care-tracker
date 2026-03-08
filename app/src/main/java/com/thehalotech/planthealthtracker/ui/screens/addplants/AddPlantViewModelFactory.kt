package com.thehalotech.planthealthtracker.ui.screens.addplants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thehalotech.planthealthtracker.data.api.PlantApiService

class AddPlantViewModelFactory(
    private val apiService: PlantApiService
) : ViewModelProvider.Factory
 {
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
         if (modelClass.isAssignableFrom(AddPlantViewModel::class.java)) {
             return AddPlantViewModel(apiService) as T

         }
         throw IllegalArgumentException("Unknown ViewModel class")
     }
}