package com.example.travelbuddy.ui.screens.travelform.models

sealed class FormResult {
    data class Created(val id: String) : FormResult()

    data class Error(val err: Exception) : FormResult()
}
