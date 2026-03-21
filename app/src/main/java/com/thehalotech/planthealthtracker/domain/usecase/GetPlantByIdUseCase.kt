package com.thehalotech.planthealthtracker.domain.usecase

import com.thehalotech.planthealthtracker.domain.repository.PlantRepository

class GetPlantByIdUseCase(
    private val repo: PlantRepository
) {
    operator fun invoke(plantId: String) = repo.getPlantById(plantId)
}
