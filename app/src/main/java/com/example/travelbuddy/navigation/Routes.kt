package com.example.travelbuddy.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute : NavKey

@Serializable
data class TravelDetailsRoute(val id: Int) : NavKey

@Serializable
data object TravelFormRoute : NavKey

@Serializable
data object LoginRoute : NavKey
