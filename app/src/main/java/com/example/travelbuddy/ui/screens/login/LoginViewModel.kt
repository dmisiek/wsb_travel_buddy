package com.example.travelbuddy.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddy.data.auth.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPending: Boolean = false,
)

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    val isLoggedIn: StateFlow<Boolean> = authRepository.userFlow
        .map { it != null }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = authRepository.getUser() != null
        )

    fun loginChanged(value: String) {
        _state.update { state -> state.copy(email = value) }
    }

    fun passwordChanged(value: String) {
        _state.update { state -> state.copy(password = value) }
    }

    fun submit() {
        val state = _state.value
        if (!state.isValid()) return

        _state.update { it.copy(isPending = true) }

        viewModelScope.launch {
            try {
                authRepository.login(
                    state.email,
                    state.password,
                )
                _state.update { it.copy(email = "", password = "") }
            } catch (e: Exception) {
                print(e) // todo handle
            } finally {
                _state.update { it.copy(isPending = false) }
            }
        }
    }
}

private fun LoginState.isValid(): Boolean {
    return email.isNotBlank() && password.isNotBlank()
}
