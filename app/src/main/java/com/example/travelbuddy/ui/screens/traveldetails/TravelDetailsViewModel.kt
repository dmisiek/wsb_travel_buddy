package com.example.travelbuddy.ui.screens.traveldetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddy.data.travels.repositories.TravelRepository
import com.example.travelbuddy.domain.travels.models.Travel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TravelDetailsState(
    val resource: Travel? = null,
)

class TravelDetailsViewModel(
    private val travelsRepository: TravelRepository,
    private val resourceId: String,
) : ViewModel() {
    private val _state = MutableStateFlow(TravelDetailsState())
    val state: StateFlow<TravelDetailsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val data = travelsRepository.get(resourceId)
            _state.update { it.copy(resource = data) }
        }
    }
}
