package com.example.travelbuddy.domain.travels.models

interface Travel {
    val id: Int
    val name: String
    val photos: List<TravelPhoto>
    val isPublic: Boolean
}
