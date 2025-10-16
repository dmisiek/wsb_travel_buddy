package com.example.travelbuddy.data.travels.repositories

import com.example.travelbuddy.core.models.Page
import com.example.travelbuddy.data.auth.repositories.AuthRepository
import com.example.travelbuddy.data.travels.mappers.toDomain
import com.example.travelbuddy.data.travels.models.NetworkTravel
import com.example.travelbuddy.domain.travels.dto.TravelDto
import com.example.travelbuddy.domain.travels.models.Travel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FirebaseTravelRepository(
    private val authRepository: AuthRepository,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
) : TravelRepository {
    fun userId(): String? {
        return authRepository.getUser()?.uid
    }

    override suspend fun getExplorePage(page: Int): Page<Travel> {
        val collection = db.collection(COLLECTION_KEY)
        val response = collection.limit(30).get().await()
        val travels = response
            .toObjects(NetworkTravel::class.java)
            .map { it.toDomain(it.userId == userId()) }

        return Page(data = travels, isLastPage = true)
    }

    override suspend fun getUserPage(page: Int): Page<Travel> {
        val collection = db.collection(COLLECTION_KEY)
        val response = collection.limit(30).get().await()
        val travels = response
            .toObjects(NetworkTravel::class.java)
            .map { it.toDomain(true) }

        return Page(data = travels, isLastPage = true)
    }

    override suspend fun get(id: String): Travel {
        val collection = db.collection(COLLECTION_KEY)
        val response = collection.document(id).get().await()
        return response
            .toObject(NetworkTravel::class.java)!!
            .let { it.toDomain(it.userId == userId()) }
    }

    override suspend fun create(dto: TravelDto) {
        val userId = authRepository.getUser()?.uid
        if (userId == null) throw Exception()

        val collection = db.collection(COLLECTION_KEY)
        val storage = storage.reference

        val path = "images/${userId}/${System.currentTimeMillis()}.jpg"
        val imageRef = storage.child(path)
        imageRef.putFile(dto.photoUri).await()
        val remoteUri = imageRef.downloadUrl.await()

        val patchedDto = dto.copy(photoUri = remoteUri)
        collection.add(patchedDto).await()
    }

    override suspend fun update(id: String, dto: TravelDto) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String) {
        TODO("Not yet implemented")
    }

    companion object {
        const val COLLECTION_KEY: String = "travels"
    }
}
