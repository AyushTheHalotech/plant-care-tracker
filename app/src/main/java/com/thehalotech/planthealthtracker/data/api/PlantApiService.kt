package com.thehalotech.planthealthtracker.data.api

import com.thehalotech.planthealthtracker.BuildConfig
import com.thehalotech.planthealthtracker.data.model.DetailsResponse
import com.thehalotech.planthealthtracker.data.model.PlantIdResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PlantApiService {

    @GET("api/v3/kb/plants/name_search")
    suspend fun searchPlantsName(
        @Query("q") query: String,
        @Header("Api-Key") apiKey: String = BuildConfig.PLANT_API_KEY,
        @Header("Content-Type") contentType: String = "application/json"
    ): PlantIdResponse

    @GET("api/v3/kb/plants/{token}")
    suspend fun getPlantDetailsByName(
        @Path("token") token: String,
        @Query("details") details: String,
        @Query("language") language: String = "en",
        @Header("Api-Key") apiKey: String = BuildConfig.PLANT_API_KEY,
        @Header("Content-Type") contentType: String = "application/json"
    ) : DetailsResponse
}