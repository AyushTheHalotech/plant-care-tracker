package com.thehalotech.planthealthtracker.domain.usecase

import android.net.Uri
import com.thehalotech.planthealthtracker.domain.model.Plant
import com.thehalotech.planthealthtracker.domain.repository.PlantRepository

class AddPlantUseCase(
    private val repo: PlantRepository
) {
    suspend operator fun invoke(plant: Plant, imageUri: Uri?) = repo.addPlant(plant, imageUri)

}