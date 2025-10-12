package com.example.travelbuddy.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginState(
    val login: String = "",
    val password: String = ""
)

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    fun loginChanged(value: String) {
        _uiState.update { state -> state.copy(login = value) }
    }

    fun passwordChanged(value: String) {
        _uiState.update { state -> state.copy(password = value) }
    }

    fun submit() {
        if (!_uiState.value.isValid()) {
            return;
        }

        viewModelScope.launch {
            TODO("Some login logic")
        }
    }
}

private fun LoginState.isValid(): Boolean {
    return login.isNotBlank() && password.isNotBlank()
}
