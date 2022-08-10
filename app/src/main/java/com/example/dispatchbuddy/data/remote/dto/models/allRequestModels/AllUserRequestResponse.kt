package com.example.dispatchbuddy.data.remote.dto.models.allRequestModels


import com.google.gson.annotations.SerializedName

data class AllUserRequestResponse(
    @SerializedName("content")
    val allUserRequestResponseContent: List<AllUserRequestResponseContent>,
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
    val allRequestResponsePageable: AllRequestResponsePageable,
    @SerializedName("size")
    val size: Int,
    @SerializedName("sort")
    val sort: AllUserRequestResponseSort,
    @SerializedName("totalElements")
    val totalElements: Int,
    @SerializedName("totalPages")
    val totalPages: Int
)