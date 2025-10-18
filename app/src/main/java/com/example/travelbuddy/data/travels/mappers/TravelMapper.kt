package com.example.travelbuddy.data.travels.mappers

import com.example.travelbuddy.data.travels.models.NetworkTravel
import com.example.travelbuddy.domain.travels.models.Travel
import androidx.core.net.toUri
import com.example.travelbuddy.domain.travels.dto.TravelDto
import com.google.firebase.Timestamp
import kotlin.String

fun NetworkTravel.toDomain(belongsToAuthedUser: Boolean): Travel {
    return Travel(
        id = id,
        name = name,
        description = description,
        photoUri = photoUri.toUri(),
        isPublic = public,
        belongsToAuthedUser = belongsToAuthedUser,
        createdAt = createdAt,
    )
}

fun TravelDto.toNetwork(
    photoUri: String,
    userId: String,
    createdAt: Timestamp
): NetworkTravel {
    return NetworkTravel(
        name = name,
        description = description,
        photoUri = photoUri,
        public = isPublic,
        userId = userId,
        createdAt = createdAt,
    )
}
