package com.example.dispatchbuddy.common

//sealed class Resource<T> (var data: T? = null, var message: String? = null) {
//    class Loading<T>() : Resource<T>()
//    class Success<T>(data: T? = null) : Resource<T>(data)
//    class Error<T>(data: T?, message: String?) : Resource<T>(data, message)
//}

sealed class Resource<out T> {
    data class Success<out T>(val value: T): Resource<T>()
    data class Loading<out T>(val value: String?) : Resource<T>()
    data class Error(val code: Int? = null, val error: String): Resource<Nothing>()
}


