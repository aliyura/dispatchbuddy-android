package com.example.dispatchbuddy.data.remote.dto.models.allRequestModels


import com.google.gson.annotations.SerializedName

data class AllRequestResponsePageable(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("pageNumber")
    val pageNumber: Int,
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("paged")
    val paged: Boolean,
    @SerializedName("sort")
    val sort: AllUserRequestResponseSort,
    @SerializedName("unpaged")
    val unpaged: Boolean
)