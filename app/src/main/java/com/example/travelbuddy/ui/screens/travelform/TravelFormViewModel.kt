package com.example.travelbuddy.ui.screens.travelform

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddy.data.travels.repositories.TravelRepository
import com.example.travelbuddy.domain.travels.dto.TravelDto
import com.example.travelbuddy.ui.screens.travelform.models.FormResult
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.String

data class TravelFormState(
    val name: String = "",
    val description: String = "",
    val photo: Uri? = null,
    val isPublic: Boolean = true,
    val isPending: Boolean = false,
    val result: FormResult? = null,
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
        val state = _state.value
        if (!state.isValid()) return

        val dto = TravelDto(
            name = state.name,
            description = state.description,
            photoUri = state.photo!!.toString(),
            isPublic = state.isPublic,
            createdAt = Timestamp.now(),
        )

        viewModelScope.launch {
            _state.update { it.copy(isPending = true) }

            try {
                val result = travelRepository.create(dto).let { FormResult.Created(id = it) }
                _state.update { it.copy(result = result) }
            } catch (e: Exception) {
                e.printStackTrace()
                _state.update { it.copy(result = FormResult.Error(e)) }
            } finally {
                _state.update { it.copy(isPending = false) }
            }
        }
    }
}

fun TravelFormState.isValid(): Boolean {
    return name.isNotBlank() && photo != null
}
