package com.thehalotech.planthealthtracker.presentation.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thehalotech.planthealthtracker.domain.usecase.GetUserUseCase
import com.thehalotech.planthealthtracker.domain.usecase.SaveUserUseCase
import kotlinx.coroutines.launch

class UserViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {

    var username: String? by mutableStateOf("Guest")
        private set

    fun loadUser() {
        viewModelScope.launch {
            username = getUserUseCase()
        }

    }

    fun saveUser(user: String?) {
        var name = ""
        if (user?.isBlank() ?: false){
            name = "Guest"
        } else {
            name = user ?: "Guest"
        }
        viewModelScope.launch {
            saveUserUseCase(name)
        }
    }

}