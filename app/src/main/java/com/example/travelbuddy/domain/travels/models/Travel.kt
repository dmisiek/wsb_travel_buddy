package com.example.travelbuddy.domain.travels.models

import android.net.Uri
import com.google.firebase.Timestamp

data class Travel(
    val id: String,
    val name: String,
    val description: String?,
    val photoUri: Uri,
    val isPublic: Boolean,
    val belongsToAuthedUser: Boolean,
    val createdAt: Timestamp,
)
