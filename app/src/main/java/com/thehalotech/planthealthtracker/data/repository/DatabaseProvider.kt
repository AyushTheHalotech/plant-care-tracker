package com.thehalotech.planthealthtracker.data.repository

import android.content.Context
import androidx.room.Room
import com.thehalotech.planthealthtracker.data.local.PlantDatabase

object DatabaseProvider {

    @Volatile
    private var INSTANCE: PlantDatabase? = null

    fun getDatabase(context: Context): PlantDatabase {
        return INSTANCE?:synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                PlantDatabase::class.java,
                "plant_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}