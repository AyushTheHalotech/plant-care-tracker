package com.thehalotech.planthealthtracker.data.remote.dto

import com.google.gson.annotations.SerializedName

data class IdentifiedPlant(
    @SerializedName("matched_in")
    val matchedIn: String,
    @SerializedName("matched_in_type")
    val matchedInType: String,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("match_position")
    val matchPosition: Int,
    @SerializedName("match_length")
    val matchLength: Int,
    @SerializedName("entity_name")
    val entityName: String
)
