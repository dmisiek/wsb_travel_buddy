package com.example.travelbuddy.data.auth.repositories

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun getUser(): FirebaseUser?

    suspend fun register(email: String, password: String): AuthResult

    suspend fun login(email: String, password: String): AuthResult

    suspend fun logout()
}
