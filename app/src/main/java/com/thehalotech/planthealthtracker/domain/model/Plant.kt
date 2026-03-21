package com.thehalotech.planthealthtracker.domain.model

data class Plant(
    val id: String,
    val name: String,
    val commonName: String,
    val wateringFrequency: Int,
    val lastWatered: Long,
    val lightRequirement: String,
    val description: String,
    val imagePath: String? = null
)