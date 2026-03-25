package com.thehalotech.planthealthtracker

import androidx.lifecycle.ViewModel
import com.thehalotech.planthealthtracker.domain.usecase.ObserveOnboardingUseCase

class MainViewModel(
    observeOnboardingUseCase: ObserveOnboardingUseCase
) : ViewModel() {

    val isOnboardingCompleted = observeOnboardingUseCase()
}