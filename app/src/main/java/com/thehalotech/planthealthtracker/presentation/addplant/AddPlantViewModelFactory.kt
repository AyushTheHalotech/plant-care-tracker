package com.thehalotech.planthealthtracker.presentation.addplant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thehalotech.planthealthtracker.data.api.PlantApiService
import com.thehalotech.planthealthtracker.domain.repository.PlantRepository
import com.thehalotech.planthealthtracker.domain.usecase.AddPlantUseCase
import com.thehalotech.planthealthtracker.domain.usecase.GetPlantDetailsUseCase
import com.thehalotech.planthealthtracker.domain.usecase.SearchPlantsUseCase

class AddPlantViewModelFactory(
    private val searchPlants: SearchPlantsUseCase,
    private val getPlantDetails: GetPlantDetailsUseCase,
    private val addPlants: AddPlantUseCase
) : ViewModelProvider.Factory
 {
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
         if (modelClass.isAssignableFrom(AddPlantViewModel::class.java)) {
             return AddPlantViewModel(searchPlants, getPlantDetails, addPlants) as T

         }
         throw IllegalArgumentException("Unknown ViewModel class")
     }
}