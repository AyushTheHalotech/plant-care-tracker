package com.thehalotech.planthealthtracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PlantEntity::class],
    version = 1,
)
abstract class PlantDatabase: RoomDatabase() {

    abstract val dao: PlantDao
}