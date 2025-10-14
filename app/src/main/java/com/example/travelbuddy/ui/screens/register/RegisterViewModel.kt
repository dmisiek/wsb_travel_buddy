package com.example.travelbuddy.ui.screens.register

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

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val passwordRepeat: String = "",
    val isPending: Boolean = false,
)

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    val isLoggedIn: StateFlow<Boolean> = authRepository.userFlow.map { it != null }.stateIn(
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

    fun passwordRepatChanged(value: String) {
        _state.update { state -> state.copy(passwordRepeat = value) }
    }

    fun submit() {
        val state = _state.value
        if (!state.isValid()) return

        _state.update { it.copy(isPending = true) }

        viewModelScope.launch {
            try {
                authRepository.register(
                    state.email,
                    state.password,
                )
                _state.update { RegisterState() }
            } catch (e: Exception) {
                print(e) // todo handle
            } finally {
                _state.update { it.copy(isPending = false) }
            }
        }
    }
}

private fun RegisterState.isValid(): Boolean {
    return email.isNotBlank() && password.isNotBlank() && passwordRepeat.isNotBlank()
            && password == passwordRepeat
}
