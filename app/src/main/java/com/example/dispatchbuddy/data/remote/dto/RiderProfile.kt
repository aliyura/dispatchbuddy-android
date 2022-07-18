package com.example.dispatchbuddy.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RiderProfile(
    val image: Int,
    val name: String,
    val mobile: String,
    val location: String,
    val dateJoined: String
): Parcelable
