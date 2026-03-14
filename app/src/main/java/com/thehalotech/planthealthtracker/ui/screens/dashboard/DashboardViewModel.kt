package com.thehalotech.planthealthtracker.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import com.thehalotech.planthealthtracker.data.repository.PlantRepository

class DashboardViewModel(
    private val repository: PlantRepository
) : ViewModel() {

    val plants = repository.plants

}