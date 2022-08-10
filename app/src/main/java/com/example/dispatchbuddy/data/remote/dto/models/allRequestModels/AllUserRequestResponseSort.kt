package com.example.dispatchbuddy.data.remote.dto.models.allRequestModels


import com.google.gson.annotations.SerializedName

data class AllUserRequestResponseSort(
    @SerializedName("empty")
    val empty: Boolean,
    @SerializedName("sorted")
    val sorted: Boolean,
    @SerializedName("unsorted")
    val unsorted: Boolean
)