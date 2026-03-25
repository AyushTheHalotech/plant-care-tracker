package com.thehalotech.planthealthtracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thehalotech.planthealthtracker.domain.usecase.ObserveOnboardingUseCase

class MainViewModelFactory(
    private val observeOnboardingUseCase: ObserveOnboardingUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(observeOnboardingUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}