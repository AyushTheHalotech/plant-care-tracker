package com.thehalotech.planthealthtracker.domain.usecase

import com.thehalotech.planthealthtracker.domain.repository.UserRepository

class SaveUserUseCase(
    private val repo: UserRepository
) {
    suspend operator fun invoke(username: String) {
        repo.saveUser(username)
        repo.setOnboardingCompleted(true)
    }
}