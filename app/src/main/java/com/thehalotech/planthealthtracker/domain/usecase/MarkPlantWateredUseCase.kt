package com.thehalotech.planthealthtracker.domain.usecase

import com.thehalotech.planthealthtracker.domain.repository.PlantRepository

class MarkPlantWateredUseCase(
    private val repo: PlantRepository
) {
    suspend operator fun invoke(plantId: String) = repo.markWatered(plantId)
}