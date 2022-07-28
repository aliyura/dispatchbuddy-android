package com.example.dispatchbuddy.data.remote.network

import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.ChangePassword
import com.example.dispatchbuddy.data.remote.dto.models.Registration
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.data.remote.dto.models.VerifyUser
import retrofit2.http.Body
import retrofit2.http.POST

interface DispatchBuddyAPI {

    @POST("user/signup")
    suspend fun registerUser(@Body registration: Registration): GenericResponse<UserProfile>

    @POST("user/verify")
    suspend fun verifyUser(@Body verifyUser: VerifyUser) : GenericResponse<UserProfile>

    @POST("user/validate")
    suspend fun validateUser(@Body email: String) : GenericResponse<String>

    @POST("user/password-reset")
    suspend fun changePassword(@Body changePassword: ChangePassword): GenericResponse<UserProfile>

//    @POST("oauth/token")
//    suspend fun loginUser(@Body )
}