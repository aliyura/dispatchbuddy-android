package com.example.dispatchbuddy.data.remote.network

import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface DispatchBuddyAPI {

    @POST("user/signup")
    suspend fun registerUser(@Body registration: Registration): GenericResponse<UserProfile>

    @POST("user/verify")
    suspend fun verifyUser(@Body verifyUser: VerifyUser) : GenericResponse<UserProfile>

    @POST("user/validate")
    suspend fun validateUser(@Body email: String) : GenericResponse<String>

    @POST("user/password-reset")
    suspend fun changePassword(@Body changePassword: ChangePassword): GenericResponse<UserProfile>

    @PUT("user/update")
    suspend fun updateProfile(@Body update: UpdateProfile, @Header("Authorization") token: String): GenericResponse<UserProfile>

    @Multipart
    @PUT("user/update-dp")
    suspend fun uploadImage(@Part dp: MultipartBody.Part, @Header("Authorization") token: String): GenericResponse<UserProfile>

    @GET("user/get-by-id/{id}")
    suspend fun getUser(@Path("id") id : String ,@Header("Authorization") token: String): GenericResponse<UserProfile>

//    @POST("oauth/token")
//    suspend fun loginUser(@Body )
}