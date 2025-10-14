package com.example.travelbuddy.data.travels.repositories

import com.example.travelbuddy.core.models.Page
import com.example.travelbuddy.data.travels.models.NetworkTravel
import com.example.travelbuddy.domain.travels.models.Travel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TravelRepositoryImpl(
    private val firestore: FirebaseFirestore,
) : TravelRepository {
    override suspend fun getExplorePage(page: Int): Page<Travel> {
        val collection = firestore.collection(COLLECTION_KEY)
        val response = collection.limit(30).get().await()
        val travels = response.toObjects(NetworkTravel::class.java)
        return Page(data = travels, isLastPage = true)
    }

    override suspend fun getUserPage(page: Int): Page<Travel> {
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

    companion object {
        const val COLLECTION_KEY: String = "travels"
    }
}
