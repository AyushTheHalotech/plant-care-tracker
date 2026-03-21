package com.thehalotech.planthealthtracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_plants")
data class PlantEntity(
    val commonName: String,
    val wateringFrequency: String,
    val lastWatered: Long,
    val lightRequirement: String,
    val description: String,
    val imagePath:String? = null,
    val plantName: String,

    @PrimaryKey(autoGenerate = false)
    val entityId: String
)
