package com.example.travelbuddy.domain.travels.dto

import android.net.Uri
import com.google.firebase.Timestamp

data class TravelDto(
    val name: String,
    val description: String?,
    val photoUri: String,
    val isPublic: Boolean,
    val createdAt: Timestamp,
)
