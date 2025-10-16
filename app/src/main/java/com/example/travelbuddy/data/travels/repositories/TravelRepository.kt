package com.example.travelbuddy.data.travels.repositories

import com.example.travelbuddy.core.models.Page
import com.example.travelbuddy.domain.travels.dto.TravelDto
import com.example.travelbuddy.domain.travels.models.Travel

interface TravelRepository {
    suspend fun getExplorePage(page: Int): Page<Travel>

    suspend fun getUserPage(page: Int): Page<Travel>

    suspend fun get(id: String): Travel

    suspend fun create(dto: TravelDto): String

    suspend fun delete(id: String)
}
