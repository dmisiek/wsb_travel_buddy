package com.example.travelbuddy.ui.screens.home

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeState(
    val user: FirebaseUser? = null,
    val exploreTravels: List<Int> = (1..20).toList(),
    val userTravels: List<Int> = (1..5).toList(),
)

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

}