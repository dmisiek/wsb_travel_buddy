package com.example.travelbuddy.ui.screens.traveldetails

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class TravelDetailsState(
    val place: String = "",
)

class TravelDetailsViewModel : ViewModel() {
    private val _state = MutableStateFlow(TravelDetailsState())
    val state: StateFlow<TravelDetailsState> = _state.asStateFlow()
}
