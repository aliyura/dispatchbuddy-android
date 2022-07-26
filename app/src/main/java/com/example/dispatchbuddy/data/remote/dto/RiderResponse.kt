package com.example.dispatchbuddy.data.remote.dto

data class RiderResponse(
    val status: String,
    val image: Int,
    val name: String,
    val location: String,
    val weight: String,
    val charge: String,
    val date: String
)
