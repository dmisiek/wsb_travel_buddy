package com.example.travelbuddy.data.travels.mappers

import com.example.travelbuddy.data.travels.models.NetworkTravel
import com.example.travelbuddy.domain.travels.models.Travel
import androidx.core.net.toUri

fun NetworkTravel.toDomain(belongsToAuthedUser: Boolean): Travel {
    return Travel(
        id = id,
        name = name,
        description = description,
        photoUri = photoUri.toUri(),
        isPublic = isPublic,
        belongsToAuthedUser = belongsToAuthedUser,
        createdAt = createdAt,
    )
}
