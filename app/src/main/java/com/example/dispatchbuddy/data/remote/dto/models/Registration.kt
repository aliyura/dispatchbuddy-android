package com.example.dispatchbuddy.data.remote.dto.models

data class Registration(
    val authProvider: String,
    val dateOfBirth: String,
    val email: String,
    val name: String,
    val password: String,
    val phoneNumber: String
)