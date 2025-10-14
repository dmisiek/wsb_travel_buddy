package com.example.travelbuddy.data.travels.models

import com.example.travelbuddy.domain.travels.models.Travel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class NetworkTravel(
    @DocumentId override val id: String = "",
    override val name: String = "",
    override val photos: List<NetworkTravelPhoto> = emptyList(),
    override val isPublic: Boolean = true,
    override val createdAt: Timestamp = Timestamp.now(),
) : Travel
