package com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel


import com.google.gson.annotations.SerializedName

data class RequestRiderContent(
    @SerializedName("accountType")
    val accountType: String,
    @SerializedName("authProvider")
    val authProvider: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("coveredLocations")
    val coveredLocations: List<String>,
    @SerializedName("createdDate")
    val createdDate: String,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String,
    @SerializedName("dp")
    val dp: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("isEnabled")
    val isEnabled: Boolean,
    @SerializedName("lastLoginDate")
    val lastLoginDate: String,
    @SerializedName("lastModifiedDate")
    val lastModifiedDate: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("thirdPartyToken")
    val thirdPartyToken: Any?,
    @SerializedName("updatedDate")
    val updatedDate: String,
    @SerializedName("uuid")
    val uuid: String
)