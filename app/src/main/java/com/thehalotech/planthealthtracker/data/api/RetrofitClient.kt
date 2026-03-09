package com.thehalotech.planthealthtracker.data.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://plant.id/"
    val gson = GsonBuilder()
        .setLenient()
        .create()


    val api: PlantApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(PlantApiService::class.java)
    }
}