package com.example.travelbuddy.data.travels.repositories

import com.example.travelbuddy.core.models.Page
import com.example.travelbuddy.domain.travels.models.Travel
import com.google.firebase.firestore.FirebaseFirestore

class TravelRepositoryImpl(
    private val firestore: FirebaseFirestore,
) : TravelRepository {
    override suspend fun getExplorePage(page: Int): List<Page<Travel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserPage(page: Int): List<Page<Travel>> {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: String): Travel {
        TODO("Not yet implemented")
    }

    override suspend fun create(travel: Travel) {
        TODO("Not yet implemented")
    }

    override suspend fun update(id: String, travel: Travel) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String) {
        TODO("Not yet implemented")
    }
}
