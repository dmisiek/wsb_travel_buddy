package com.example.travelbuddy.domain.travels.dto

data class TravelDto(
    val name: String,
    val description: String?,
    val photoUri: String,
    val isPublic: Boolean,
)
