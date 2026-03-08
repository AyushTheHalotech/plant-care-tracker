package com.thehalotech.planthealthtracker.data.model

import com.google.gson.annotations.SerializedName

data class PlantDetails(
    val id: Int,
    @SerializedName("common_name")
    val commonName: String?,
    @SerializedName("scientific_name")
    val scientificName: List<String>?,
    @SerializedName("watering_general_benchmark")
    val wateringBenchmark : WaterBenchmark?,
    val sunlight: List<String>?,
    val description: String?,

)
