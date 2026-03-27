package com.thehalotech.planthealthtracker.domain.repository


import android.net.Uri
import com.thehalotech.planthealthtracker.domain.model.IdentifiedPlantDomain
import com.thehalotech.planthealthtracker.domain.model.Plant
import com.thehalotech.planthealthtracker.domain.model.PlantDetailsDomain
import kotlinx.coroutines.flow.Flow

interface PlantRepository {

    fun getPlants(): Flow<List<Plant>>

    suspend fun addPlant(plant: Plant, imageUri: Uri?)

    suspend fun searchPlants(plantName: String): List<IdentifiedPlantDomain>

    suspend fun getPlantDetails(plantId: String): PlantDetailsDomain

    suspend fun markWatered(plantId: String)

    fun getPlantById(plantId: String): Flow<Plant?>

}