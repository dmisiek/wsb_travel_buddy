package com.example.travelbuddy.data.travels.models

import com.example.travelbuddy.domain.travels.models.TravelPhoto

data class NetworkTravelPhoto(
    override val url: String, // todo: urls to photos?
    override val description: String?,
) : TravelPhoto
