package com.example.travelbuddy.data.auth.repositories

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val userFlow: Flow<FirebaseUser?>

    fun getUser(): FirebaseUser?

    suspend fun register(email: String, password: String): AuthResult

    suspend fun login(email: String, password: String): AuthResult

    suspend fun logout()
}
