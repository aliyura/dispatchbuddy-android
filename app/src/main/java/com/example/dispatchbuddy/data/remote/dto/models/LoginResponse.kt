package com.example.dispatchbuddy.data.remote.dto.models

data class LoginResponse(
    val access_token: String,
    val accountType: String,
    val city: String,
    val dp: String,
    val email: String,
    val gender: String,
    val id: String,
    val isEnabled: Boolean,
    val jti: String,
    val name: String,
    val phoneNumber: String,
    val refresh_token: String,
    val role: String,
    val scope: String,
    val status: String,
    val token_type: String,
    val uuid: String
)