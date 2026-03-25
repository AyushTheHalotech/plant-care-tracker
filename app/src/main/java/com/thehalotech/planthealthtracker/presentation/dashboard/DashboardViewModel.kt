package com.thehalotech.planthealthtracker.presentation.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thehalotech.planthealthtracker.domain.repository.PlantRepository
import com.thehalotech.planthealthtracker.domain.usecase.GetPlantsUseCase
import com.thehalotech.planthealthtracker.domain.usecase.GetUserUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getPlantsUseCase: GetPlantsUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    var username: String? by mutableStateOf("Guest")
        private set

    fun loadUser() {
        viewModelScope.launch {
            username = getUserUseCase()
        }

    }
    val plants = getPlantsUseCase().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

}