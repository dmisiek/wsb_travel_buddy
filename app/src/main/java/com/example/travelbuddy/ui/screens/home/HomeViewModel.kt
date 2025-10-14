package com.example.travelbuddy.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddy.data.auth.repositories.AuthRepository
import com.example.travelbuddy.data.travels.repositories.TravelRepository
import com.example.travelbuddy.domain.travels.models.Travel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeState(
    val exploreTravels: List<Travel> = emptyList(),
    val userTravels: List<Travel> = emptyList(),
)

class HomeViewModel(
    private val authRepository: AuthRepository,
    private val travelRepository: TravelRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    val user: StateFlow<FirebaseUser?> = authRepository.userFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = authRepository.getUser()
        )

    init {
        viewModelScope.launch {
            try {
                val travelsPage = travelRepository.getExplorePage(1)

                _state.update { it.copy(exploreTravels = travelsPage.data) }
            } catch (e: Exception) {
                print(e) // todo handle
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                authRepository.logout()
                _state.update { it.copy(userTravels = emptyList()) }
            } catch (e: Exception) {
                print(e) // todo handle
            }
        }
    }
}
