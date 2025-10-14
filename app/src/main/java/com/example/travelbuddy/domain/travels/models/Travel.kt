package com.example.travelbuddy.domain.travels.models

interface Travel {
    val id: String
    val name: String
    val photos: List<TravelPhoto>
    val isPublic: Boolean
    val createdAt: String
}
