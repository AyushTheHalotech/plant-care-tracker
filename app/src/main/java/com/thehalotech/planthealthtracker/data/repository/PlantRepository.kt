package com.thehalotech.planthealthtracker.data.repository

import android.content.Context
import android.net.Uri
import com.thehalotech.planthealthtracker.data.local.MyPlantsTable
import com.thehalotech.planthealthtracker.data.local.PlantDao
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate

class PlantRepository(private val dao: PlantDao, private val context: Context) {

    val plants = dao.getAllPlants()

    suspend fun addPlant(plant: MyPlantsTable, uri: Uri?) {

        var imagePath: String? = null;

        uri?.let {
            val fileName = "plant_${plant.plantName}${System.currentTimeMillis()}.jpg"
            imagePath = saveImageToStorage(context, fileName, it)
        }

        val plantWithImagePath = plant.copy(imagePath = imagePath)
        dao.addPlant(plantWithImagePath)
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

    fun getPlant(plantName: String): Flow<MyPlantsTable> {
        return dao.getPlantByName(plantName)
    }

    suspend fun markWatered(plantName: String) {
        val today = LocalDate.now().toEpochDay()
        dao.updateLastWatered(plantName, today)
    }

}