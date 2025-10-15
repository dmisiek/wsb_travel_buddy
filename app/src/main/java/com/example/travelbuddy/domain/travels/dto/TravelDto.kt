package com.example.travelbuddy.domain.travels.dto

import android.net.Uri
import com.google.firebase.Timestamp

data class TravelDto(
    val name: String,
    val photos: List<TravelPhotoDto>,
    val isPublic: Boolean,
    val createdAt: Timestamp,
)

data class TravelPhotoDto(
    val uri: Uri,
    val remoteUri: Uri?,
    val description: String?,
)
