package com.thehalotech.planthealthtracker.domain.usecase

import com.thehalotech.planthealthtracker.domain.repository.PlantRepository

class GetPlantDetailsUseCase(
    private val repo: PlantRepository
) {
    suspend operator fun invoke(id: String) = repo.getPlantDetails(id)
}