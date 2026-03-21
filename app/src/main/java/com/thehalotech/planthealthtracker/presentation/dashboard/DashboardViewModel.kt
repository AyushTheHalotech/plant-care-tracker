package com.thehalotech.planthealthtracker.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thehalotech.planthealthtracker.domain.repository.PlantRepository
import com.thehalotech.planthealthtracker.domain.usecase.GetPlantsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class DashboardViewModel(
    private val getPlantsUseCase: GetPlantsUseCase
) : ViewModel() {

    val plants = getPlantsUseCase().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

}