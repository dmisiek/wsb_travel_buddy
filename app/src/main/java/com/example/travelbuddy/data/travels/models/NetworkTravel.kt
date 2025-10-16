package com.example.travelbuddy.data.travels.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class NetworkTravel(
    @DocumentId val id: String = "",
    val name: String = "",
    val description: String? = null,
    val photoUri: String = "",
    val isPublic: Boolean = true,
    val userId: String = "",
    val createdAt: Timestamp = Timestamp.now(),
)
