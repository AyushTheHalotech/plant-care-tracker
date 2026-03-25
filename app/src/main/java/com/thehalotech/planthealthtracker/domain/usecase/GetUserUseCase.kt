package com.thehalotech.planthealthtracker.domain.usecase

import com.thehalotech.planthealthtracker.domain.repository.UserRepository

class GetUserUseCase(
    private val repo: UserRepository
) {
    suspend operator fun invoke() = repo.getUser()
}
