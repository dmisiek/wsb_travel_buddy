package com.example.travelbuddy.data.travels.models

import com.example.travelbuddy.domain.travels.models.Travel

data class NetworkTravel(
    override val id: Int,
    override val name: String,
    override val photos: List<NetworkTravelPhoto>,
    override val isPublic: Boolean,
) : Travel
