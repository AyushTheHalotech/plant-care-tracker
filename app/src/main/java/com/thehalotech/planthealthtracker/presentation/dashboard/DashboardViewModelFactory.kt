package com.thehalotech.planthealthtracker.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thehalotech.planthealthtracker.domain.repository.PlantRepository
import com.thehalotech.planthealthtracker.domain.usecase.GetPlantsUseCase
import com.thehalotech.planthealthtracker.domain.usecase.GetUserUseCase

class DashboardViewModelFactory(
    private val getPlantsUseCase: GetPlantsUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(getPlantsUseCase, getUserUseCase) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}