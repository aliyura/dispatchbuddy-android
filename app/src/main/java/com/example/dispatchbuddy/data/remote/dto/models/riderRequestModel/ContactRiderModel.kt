package com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel


import com.google.gson.annotations.SerializedName

data class ContactRiderModel(
    @SerializedName("destination")
    val destination: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("pickupLocation")
    val pickupLocation: String,
    @SerializedName("riderUuid")
    val riderUuid: String?
)