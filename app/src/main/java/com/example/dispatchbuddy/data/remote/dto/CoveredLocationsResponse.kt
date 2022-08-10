package com.example.dispatchbuddy.data.remote.dto

data class CoveredLocationsResponse(
    val accountType: String,
    val authProvider: String,
    val city: String,
    val country: String,
    val coveredLocations: List<String>,
    val createdDate: String,
    val dateOfBirth: String,
    val dp: String,
    val email: String,
    val gender: String,
    val id: String,
    val isEnabled: Boolean,
    val lastLoginDate: String,
    val lastModifiedDate: String,
    val name: String,
    val password: String,
    val phoneNumber: String,
    val role: String,
    val status: String,
    val thirdPartyToken: Any,
    val updatedDate: String,
    val uuid: String
)