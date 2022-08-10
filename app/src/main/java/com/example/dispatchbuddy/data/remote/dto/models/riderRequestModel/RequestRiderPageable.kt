package com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel


import com.google.gson.annotations.SerializedName

data class RequestRiderPageable(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("pageNumber")
    val pageNumber: Int,
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("paged")
    val paged: Boolean,
    @SerializedName("sort")
    val sort: RequestRiderPagableSort,
    @SerializedName("unpaged")
    val unpaged: Boolean
)