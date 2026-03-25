package com.thehalotech.planthealthtracker.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUser(): String?

    suspend fun saveUser(username: String)

    suspend fun setOnboardingCompleted(completed: Boolean)

    val isOnboardingCompleted: Flow<Boolean>


}