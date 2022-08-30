package com.example.dispatchbuddy.data.remote.dto.models.allRequestModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "All_Requests")
data class AllUserRequestResponseContent(
    @PrimaryKey
    val roomDbId: Int? = null,
    @SerializedName("createdDate")
    val createdDate: String,
    @SerializedName("destination")
    val destination: String,
    @SerializedName("id")
    var id: String,
    @SerializedName("lastModifiedDate")
    val lastModifiedDate: String?,
    @SerializedName("pickupLocation")
    val pickupLocation: String,
    @SerializedName("requestId")
    val requestId: String,
    @SerializedName("riderName")
    val riderName: String,
    @SerializedName("riderPhone")
    val riderPhone: String,
    @SerializedName("riderUuid")
    val riderUuid: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("statusReason")
    val statusReason: String?,
    @SerializedName("userEmail")
    val userEmail: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("userPhoneNumber")
    val userPhoneNumber: String
)