package com.thehalotech.planthealthtracker.application

import android.content.Context
import androidx.room.Room
import com.thehalotech.planthealthtracker.data.api.RetrofitClient
import com.thehalotech.planthealthtracker.data.local.PlantDatabase
import com.thehalotech.planthealthtracker.data.repository.DatabaseProvider
import com.thehalotech.planthealthtracker.data.repository.PlantRepository

class AppContainer(context: Context) {
    private val database = DatabaseProvider.getDatabase(context)
    private val plantDao = database.dao
    val plantRepository = PlantRepository(plantDao, context)

    val plantApi = RetrofitClient.api
}