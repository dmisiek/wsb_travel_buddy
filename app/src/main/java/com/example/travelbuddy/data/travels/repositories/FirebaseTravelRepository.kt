package com.example.travelbuddy.data.travels.repositories

import com.example.travelbuddy.core.models.Page
import com.example.travelbuddy.data.auth.repositories.AuthRepository
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
    override suspend fun getExplorePage(page: Int): Page<Travel> {
        val collection = db.collection(COLLECTION_KEY)
        val response = collection.limit(30).get().await()
        val travels = response.toObjects(NetworkTravel::class.java)
        return Page(data = travels, isLastPage = true)
    }

    override suspend fun getUserPage(page: Int): Page<Travel> {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: String): Travel {
        val collection = db.collection(COLLECTION_KEY)
        val response = collection.document(id).get().await()
        return response.toObject(NetworkTravel::class.java)!!
    }

    override suspend fun create(dto: TravelDto) {
        val userId = authRepository.getUser()?.uid
        if (userId == null) throw Exception()

        val collection = db.collection(COLLECTION_KEY)
        val storage = storage.reference

        val uploadedPhotos = dto.photos.map {
            val path = "images/${userId}/${System.currentTimeMillis()}.jpg"
            val imageRef = storage.child(path)

            imageRef.putFile(it.uri).await()

            val uri = imageRef.downloadUrl.await()

            it.copy(remoteUri = uri)
        }

        val patchedDto = dto.copy(photos = uploadedPhotos)
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
