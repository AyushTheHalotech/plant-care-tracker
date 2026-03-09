package com.thehalotech.planthealthtracker.application

import android.app.Application

class PlantApp: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }

}