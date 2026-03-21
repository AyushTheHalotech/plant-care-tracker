package com.thehalotech.planthealthtracker.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thehalotech.planthealthtracker.domain.repository.PlantRepository
import com.thehalotech.planthealthtracker.domain.usecase.GetPlantsUseCase

class DashboardViewModelFactory(
    private val getPlantsUseCase: GetPlantsUseCase
) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(getPlantsUseCase) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}