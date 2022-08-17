package com.example.dispatchbuddy.data.remote.dto.models.pagingDTO


import com.google.gson.annotations.SerializedName

data class PagingResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("payload")
    val payload: Payload,
    @SerializedName("success")
    val success: Boolean
)