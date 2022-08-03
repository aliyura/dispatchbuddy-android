package com.example.dispatchbuddy.data.remote.dto.models

data class UserProfile(
    val accountType: String,
    val authProvider: String,
    val city: String,
    val country: String,
    val createdDate: String,
    val dateOfBirth: String,
    val dp: Any,
    val email: String,
    val gender: String,
    val id: String? = null,
    val isEnabled: Boolean,
    val lastLoginDate: String,
    val lastModifiedDate: Any,
    val name: String,
    val password: String,
    val phoneNumber: String,
    val role: String,
    val status: String,
    val thirdPartyToken: Any,
    val updatedDate: Any,
    val uuid: String
)