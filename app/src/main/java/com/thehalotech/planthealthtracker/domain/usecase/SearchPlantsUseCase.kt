package com.thehalotech.planthealthtracker.domain.usecase

import com.thehalotech.planthealthtracker.domain.repository.PlantRepository

class SearchPlantsUseCase(private val repo: PlantRepository) {
    suspend operator fun invoke(query: String) =
        repo.searchPlants(query)
}