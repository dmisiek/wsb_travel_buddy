package com.example.travelbuddy.data.travels.repositories

import android.net.Uri
import androidx.core.net.toUri
import com.example.travelbuddy.core.models.Page
import com.example.travelbuddy.data.auth.repositories.AuthRepository
import com.example.travelbuddy.data.travels.mappers.toDomain
import com.example.travelbuddy.data.travels.mappers.toNetwork
import com.example.travelbuddy.data.travels.models.NetworkTravel
import com.example.travelbuddy.domain.travels.dto.TravelDto
import com.example.travelbuddy.domain.travels.models.Travel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

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
        var query = collection.whereEqualTo("public", true)
        if (userId() != null) {
            query = query.whereNotEqualTo("userId", userId())
        }

        val response = query.get().await()
        val travels = response
            .toObjects(NetworkTravel::class.java)
            .map { it.toDomain(true) }

        return Page(data = travels, isLastPage = travels.count() < PAGE_SIZE)
    }


    override suspend fun getUserPage(page: Int): Page<Travel> {
        val collection = db.collection(COLLECTION_KEY)
        val query =
            if (userId() != null) collection.whereEqualTo("userId", userId())
            else collection

        val response = query.get().await()
        val travels = response
            .toObjects(NetworkTravel::class.java)
            .map { it.toDomain(true) }

        return Page(data = travels, isLastPage = travels.count() < PAGE_SIZE)
    }

    override suspend fun get(id: String): Travel {
        val collection = db.collection(COLLECTION_KEY)
        val response = collection.document(id).get().await()
        return response.toObject(NetworkTravel::class.java)!!
            .let { it.toDomain(it.userId == userId()) }
    }

    override suspend fun create(dto: TravelDto): String {
        val userId = userId()
        if (userId == null) throw Exception()

        val collection = db.collection(COLLECTION_KEY)
        val storage = storage.reference

        val localUri = dto.photoUri.toUri()
        val remotePath = "images/${userId}/${System.currentTimeMillis()}.jpg"
        val imageRef = storage.child(remotePath)
        imageRef.putFile(localUri).await()
        val remoteUri = imageRef.downloadUrl.await()

        val networkTravel = dto.toNetwork(
            photoUri = remoteUri.toString(), userId = userId, createdAt = Timestamp.now()
        )
        val result = collection.add(networkTravel).await()
        return result.id
    }

    override suspend fun delete(id: String) {
        val collection = db.collection(COLLECTION_KEY)
        val storage = storage.reference

        val imagePath = getStorageRelativePath(get(id).photoUri)
        if (imagePath != null) {
            val imageRef = storage.child(imagePath)
            imageRef.delete().await()
        }

        collection.document(id).delete().await()
    }

    fun getStorageRelativePath(uri: Uri): String? {
        val uriString = uri.toString()

        val startSegment = "/o/"
        val startIndex = uriString.indexOf(startSegment)

        if (startIndex == -1) return null

        val pathStart = startIndex + startSegment.length
        val pathEnd = uriString.indexOf('?', pathStart)

        val encodedPath = if (pathEnd != -1) {
            uriString.substring(pathStart, pathEnd)
        } else {
            uriString.substring(pathStart)
        }

        return URLDecoder.decode(encodedPath, StandardCharsets.UTF_8.toString())
    }

    companion object {
        const val COLLECTION_KEY: String = "travels"
        const val PAGE_SIZE: Int = 30
    }
}
