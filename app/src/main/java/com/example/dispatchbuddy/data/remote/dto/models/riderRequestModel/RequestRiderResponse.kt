package com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel


import com.google.gson.annotations.SerializedName

data class RequestRiderResponse(
    @SerializedName("content")
    val requestRiderContent: List<RequestRiderContent>,
    @SerializedName("empty")
    val empty: Boolean,
    @SerializedName("first")
    val first: Boolean,
    @SerializedName("last")
    val last: Boolean,
    @SerializedName("number")
    val number: Int,
    @SerializedName("numberOfElements")
    val numberOfElements: Int,
    @SerializedName("pageable")
    val requestRiderPageable: RequestRiderPageable,
    @SerializedName("size")
    val size: Int,
    @SerializedName("sort")
    val sort: RequestRiderPagableSort,
    @SerializedName("totalElements")
    val totalElements: Int,
    @SerializedName("totalPages")
    val totalPages: Int
)