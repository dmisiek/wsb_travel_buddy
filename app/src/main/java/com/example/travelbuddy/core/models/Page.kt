package com.example.travelbuddy.core.models

data class Page<T>(
    val data: List<T>,
    val isLastPage: Boolean,
)
