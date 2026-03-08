package com.thehalotech.planthealthtracker.data.model

import com.google.gson.annotations.SerializedName

data class PlantSearchItem(
    val id: Int,
    @SerializedName("common_name")
    val commonName: String?,
    @SerializedName("scientific_name")
    val scientificName: List<String>?
)
