package com.thehalotech.planthealthtracker.data.repository

import com.thehalotech.planthealthtracker.data.local.MyPlantsTable
import com.thehalotech.planthealthtracker.data.local.PlantDao
import kotlinx.coroutines.flow.Flow

class PlantRepository(private val dao: PlantDao) {

    val plants = dao.getAllPlants()

    suspend fun addPlant(plant: MyPlantsTable) {
        dao.addPlant(plant)
    }

}