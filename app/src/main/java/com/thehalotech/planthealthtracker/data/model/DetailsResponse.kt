package com.thehalotech.planthealthtracker.data.model

import com.google.gson.annotations.SerializedName

data class DetailsResponse(
    @SerializedName("common_names")
    val commonNames: List<String>,
    val description: Description,
    val watering: Watering?,
    @SerializedName("best_light_condition")
    val bestLightCondition: String,
    val language: String,
    @SerializedName("entity_id")
    val entityId: String,
    val name: String
)

data class Description(
    val value: String,
    val citation: String,
    @SerializedName("license_name")
    val licenseName: String,
    @SerializedName("license_url")
    val licenseUrl: String
)

data class Watering(
    val max: Int,
    val min: Int
)
