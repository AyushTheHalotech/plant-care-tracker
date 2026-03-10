package com.thehalotech.planthealthtracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_plants")
data class MyPlantsTable(
    val commonName: String,
    val wateringFrequency: String,
    val lastWatered: Long,
    val lightRequirement: String,
    val description: String,
    val imagePath:String? = null,

    @PrimaryKey(autoGenerate = false)
    val plantName: String
)
