package com.thehalotech.planthealthtracker.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.thehalotech.planthealthtracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserRepositoryImpl(
    private val context: Context
): UserRepository {

    companion object {
        val USERNAME_KEY = stringPreferencesKey("username")
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    }

    override suspend fun getUser(): String? {
        return context.dataStore.data
            .map { preferences -> preferences[USERNAME_KEY] ?: "" }
            .first()
    }

    override suspend fun saveUser(username: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit {
            it[ONBOARDING_COMPLETED] = completed
        }
    }

    override val isOnboardingCompleted: Flow<Boolean> =
        context.dataStore.data.map {
            it[ONBOARDING_COMPLETED] ?: false
        }
}