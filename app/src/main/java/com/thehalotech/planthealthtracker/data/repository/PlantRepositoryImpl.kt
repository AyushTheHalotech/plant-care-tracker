package com.thehalotech.planthealthtracker.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.thehalotech.planthealthtracker.data.api.PlantApiService
import com.thehalotech.planthealthtracker.data.local.PlantDao
import com.thehalotech.planthealthtracker.domain.model.Plant
import com.thehalotech.planthealthtracker.domain.model.PlantDetailsDomain
import com.thehalotech.planthealthtracker.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import androidx.core.net.toUri
import com.thehalotech.planthealthtracker.data.mapper.toDomain
import com.thehalotech.planthealthtracker.data.mapper.toEntity
import com.thehalotech.planthealthtracker.domain.model.IdentifiedPlantDomain
import kotlinx.coroutines.flow.map
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate

class PlantRepositoryImpl(
    private val plantDao: PlantDao,
    private val plantApi: PlantApiService,
    private val context: Context
): PlantRepository {

    companion object {
        private const val TAG = "TheHaloTech::PlantRepositoryImpl"
    }
    override fun getPlants(): Flow<List<Plant>> {
        return plantDao.getAllPlants().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addPlant(
        plant: Plant,
        imageUri: Uri?
    ) {
        val imagePath = imageUri?.let {
            saveImageToStorage(context, "plant_${System.currentTimeMillis()}.jpg", it)
        }

        val entity = plant.toEntity(imagePath)
        plantDao.addPlant(entity)
    }

    override suspend fun searchPlants(plantName: String): List<IdentifiedPlantDomain> {
        return plantApi.searchPlantsName(plantName).toDomain()
    }

    override suspend fun getPlantDetails(plantId: String): PlantDetailsDomain {
        Log.i(TAG, "Fetching plant details")
        val details = "common_names,description,watering,best_light_condition"
        val response = plantApi.getPlantDetailsByName(
            plantId,
            details
        )
        return response.toDomain()
    }

    override suspend fun markWatered(plantId: String) {
        val today = LocalDate.now().toEpochDay()
        plantDao.updateLastWatered(plantId, today)
    }

    override fun getPlantById(plantId: String): Flow<Plant> {
        return plantDao.getPlantById(plantId)
            .map {entity ->
                entity?.toDomain() as Plant
            }
    }

    private fun saveImageToStorage(context: Context,fileName: String, uri: Uri) : String {
        val plantDir = File(context.filesDir, "plants")

        if (!plantDir.exists()) {
            plantDir.mkdir()
        }

        val file = File(
            plantDir,
            fileName
        )

        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)

        inputStream?.copyTo(outputStream)

        inputStream?.close()
        outputStream.close()

        return file.absolutePath

    }
}