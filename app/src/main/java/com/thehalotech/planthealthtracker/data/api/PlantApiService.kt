package com.thehalotech.planthealthtracker.data.api

import com.thehalotech.planthealthtracker.data.model.DetailsResponse
import com.thehalotech.planthealthtracker.data.model.PlantDetails
import com.thehalotech.planthealthtracker.data.model.PlantQueryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlantApiService {
    @GET("api/v2/species-list")
    suspend fun searchPlants(
        @Query("key") apiKey: String,
        @Query("q") search: String
    ): PlantQueryResponse

    //{{base_url}}/api/v2/species/details/{{plant_id}}?key={{key}}
    @GET(value = "api/v2/species/details")
    suspend fun getPlantDetails(
        @Path("plant_id") plantId: Int,
        @Query("key") apiKey: String
    ): PlantDetails

    @GET(value = "api/v2/species/details/{plant_id}")
    suspend fun getPlantDetailsRaw(
        @Path("plant_id") plantId: Int,
        @Query("key") apiKey: String
    ): Response<String>
}