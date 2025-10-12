package com.example.travelbuddy.ui.screens.travelform

import androidx.lifecycle.ViewModel
import com.example.travelbuddy.ui.screens.home.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class TravelFormState(
    val place: String = "",
)

class TravelFormViewModel : ViewModel() {
    private val _state = MutableStateFlow(TravelFormState())
    val state: StateFlow<TravelFormState> = _state.asStateFlow()
}
