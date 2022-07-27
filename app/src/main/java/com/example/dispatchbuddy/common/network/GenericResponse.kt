package com.example.dispatchbuddy.common.network

data class GenericResponse<T>(
    val message: String,
    val success: Boolean,
    val payload: T,
    )