package com.example.travelbuddy.data.travels.repositories

import com.example.travelbuddy.core.models.Page
import com.example.travelbuddy.domain.travels.models.Travel

class TravelRepositoryImpl : TravelRepository {
    override suspend fun getExplorePage(page: Int): List<Page<Travel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserPage(page: Int): List<Page<Travel>> {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: Int): Travel {
        TODO("Not yet implemented")
    }
}
