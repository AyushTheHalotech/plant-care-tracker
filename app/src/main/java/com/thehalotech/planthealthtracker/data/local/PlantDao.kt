package com.thehalotech.planthealthtracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {

    @Upsert
    suspend fun addPlant(myPlant: MyPlantsTable)

    @Delete
    suspend fun deletePlant(myPlant: MyPlantsTable)


    @Query("SELECT * FROM my_plants")
    fun getAllPlants(): Flow<List<MyPlantsTable>>

}