package com.example.travelbuddy.ui.screens.travelform

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddy.data.travels.repositories.TravelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TravelFormState(
    val name: String = "",
    val description: String = "",
    val photo: Uri? = null,
)

class TravelFormViewModel(
    private val travelRepository: TravelRepository
) : ViewModel() {
    private val _state = MutableStateFlow(TravelFormState())
    val state: StateFlow<TravelFormState> = _state.asStateFlow()

    fun nameChanged(value: String) {
        _state.update { it.copy(name = value) }
    }

    fun descriptionChanged(value: String) {
        _state.update { it.copy(description = value) }
    }

    fun photoChanged(value: Uri?) {
        _state.update { it.copy(photo = value) }
    }

    fun submit() {
        viewModelScope.launch {
            // todo
        }
    }
}
