package com.thehalotech.planthealthtracker.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PlantIdResponse(
    @SerializedName("entities")
    val entities: List<IdentifiedPlant>,
    @SerializedName("entities_trimmed")
    val entitiesTrimmed: Boolean,
    @SerializedName("limit")
    val limit: Int
)