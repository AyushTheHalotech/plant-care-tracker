package com.thehalotech.planthealthtracker.domain.model

data class PlantDetailsDomain(
    val id: String,
    val name: String,
    val description: String,
    val wateringFreq: Int,
    val lightRequirement: String,
    val commonName: String
)
