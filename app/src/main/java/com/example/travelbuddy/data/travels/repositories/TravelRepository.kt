package com.example.travelbuddy.data.travels.repositories

import com.example.travelbuddy.core.models.Page
import com.example.travelbuddy.domain.travels.models.Travel

interface TravelRepository {
    suspend fun getExplorePage(page: Int): List<Page<Travel>>

    suspend fun getUserPage(page: Int): List<Page<Travel>>

    suspend fun get(id: Int): Travel
}
