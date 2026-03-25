package com.thehalotech.planthealthtracker.domain.usecase

import com.thehalotech.planthealthtracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class ObserveOnboardingUseCase(
    private val userRepository: UserRepository

) {
    operator fun invoke(): Flow<Boolean> {
        return userRepository.isOnboardingCompleted
    }
}