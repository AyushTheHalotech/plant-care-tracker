package com.thehalotech.planthealthtracker.application

import android.content.Context
import com.thehalotech.planthealthtracker.data.api.RetrofitClient
import com.thehalotech.planthealthtracker.data.di.DatabaseProvider
import com.thehalotech.planthealthtracker.data.repository.PlantRepositoryImpl
import com.thehalotech.planthealthtracker.data.repository.UserRepositoryImpl
import com.thehalotech.planthealthtracker.domain.repository.PlantRepository
import com.thehalotech.planthealthtracker.domain.repository.UserRepository
import com.thehalotech.planthealthtracker.domain.usecase.AddPlantUseCase
import com.thehalotech.planthealthtracker.domain.usecase.GetPlantByIdUseCase
import com.thehalotech.planthealthtracker.domain.usecase.GetPlantDetailsUseCase
import com.thehalotech.planthealthtracker.domain.usecase.GetPlantsUseCase
import com.thehalotech.planthealthtracker.domain.usecase.GetUserUseCase
import com.thehalotech.planthealthtracker.domain.usecase.MarkPlantWateredUseCase
import com.thehalotech.planthealthtracker.domain.usecase.ObserveOnboardingUseCase
import com.thehalotech.planthealthtracker.domain.usecase.SaveUserUseCase
import com.thehalotech.planthealthtracker.domain.usecase.SearchPlantsUseCase

class AppContainer(context: Context) {
    private val database = DatabaseProvider.getDatabase(context)
    private val plantDao = database.dao
    val plantApi = RetrofitClient.api
    val plantRepository = PlantRepositoryImpl(plantDao, plantApi, context)

    val userRepo = UserRepositoryImpl(context)

    val addPlantUseCase = AddPlantUseCase(plantRepository)

    val getPlantsUseCase = GetPlantsUseCase(plantRepository)

    val getPlantByIdUseCase = GetPlantByIdUseCase(plantRepository)

    val searchPlantsUseCase = SearchPlantsUseCase(plantRepository)

    val getPlantDetailsUseCase = GetPlantDetailsUseCase(plantRepository)

    val markPlantWatered = MarkPlantWateredUseCase(plantRepository)

    val saveUser = SaveUserUseCase(userRepo)

    val getUser = GetUserUseCase(userRepo)

    val observeOnboardingUseCase = ObserveOnboardingUseCase(userRepo)
}