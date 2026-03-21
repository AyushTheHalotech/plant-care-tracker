package com.thehalotech.planthealthtracker.domain.usecase

import com.thehalotech.planthealthtracker.domain.repository.PlantRepository

class GetPlantsUseCase(
    private val repo: PlantRepository
) {
    operator fun invoke() = repo.getPlants()
}