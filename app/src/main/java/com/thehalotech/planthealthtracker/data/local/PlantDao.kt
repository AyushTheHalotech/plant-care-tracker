package com.thehalotech.planthealthtracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {

    @Upsert
    suspend fun addPlant(myPlant: PlantEntity)

    @Delete
    suspend fun deletePlant(myPlant: PlantEntity)


    @Query("SELECT * FROM my_plants")
    fun getAllPlants(): Flow<List<PlantEntity>>

    @Query("SELECT * FROM my_plants WHERE entityId = :plantId")
    fun getPlantById(plantId: String): Flow<PlantEntity?>

    @Query("UPDATE my_plants SET lastWatered = :date WHERE plantName = :plantName")
    fun updateLastWatered(plantName: String, date: Long)

}