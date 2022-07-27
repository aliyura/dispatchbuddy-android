package com.example.dispatchbuddy.common

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext

suspend fun <T> apiCall(dispatcher: CoroutineContext = Dispatchers.IO,
                        apiCall: suspend () -> T) : Resource<T> {
    return withContext(dispatcher) {
        try {
            Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable){
            throwable.printStackTrace()
            when(throwable) {
                is UnknownHostException ->
                    Resource.Error(101, ErrorStatus.NO_CONNECTION)
                is SocketTimeoutException ->
                    Resource.Error(102, ErrorStatus.TIMEOUT)
                is HttpException -> {
                    val body = throwable.response()!!.errorBody()!!.string()
                    val gson = Gson()
                    try {
                        val response = gson.fromJson(body, APIErrorResponse::class.java)
                        if(response != null){
                            val apiErrorMessage = response.message
                            Resource.Error(401, apiErrorMessage)
                        } else {
                            Resource.Error(401, throwable.message())
                        }
                    } catch (exception: Exception){
                        Resource.Error(401, "An error occurred on the server")
                    }

                }
                is InternalError ->
                    Resource.Error(500, "An internal server error occurred")

                is IOException ->
                    Resource.Error(103, "Not connected to the internet")
                else -> {
                    Resource.Error(null, throwable.localizedMessage)
                }
            }
        }
    }

}

class ErrorStatus {
    companion object {
        const val NO_CONNECTION = "Not Connected To The Internet"
        const val UNAUTHORIZED = "You are not Authorized to perform this action"
        const val TIMEOUT = "Request has been Timed out"
        const val EMPTY_RESPONSE = "No data found"
    }
}

@Parcelize
data class APIErrorResponse(
    val message: String
) : Parcelable
