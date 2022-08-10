package com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel


import com.google.gson.annotations.SerializedName

data class RequestRiderPagableSort(
    @SerializedName("empty")
    val empty: Boolean,
    @SerializedName("sorted")
    val sorted: Boolean,
    @SerializedName("unsorted")
    val unsorted: Boolean
)