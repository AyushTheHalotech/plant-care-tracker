package com.thehalotech.planthealthtracker.ui.screens.plantlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thehalotech.planthealthtracker.data.repository.PlantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlantlistViewModel(
    private val repository: PlantRepository
) : ViewModel() {

    val plants = repository.plants

    fun markWatered(plantName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.markWatered(plantName)
        }
    }
    
}