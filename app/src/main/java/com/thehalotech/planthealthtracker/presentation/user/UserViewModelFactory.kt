package com.thehalotech.planthealthtracker.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thehalotech.planthealthtracker.domain.usecase.GetUserUseCase
import com.thehalotech.planthealthtracker.domain.usecase.SaveUserUseCase


class UserViewModelFactory(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(getUserUseCase, saveUserUseCase) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}