package com.example.dispatchbuddy.data.remote.network

import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.LoginResponse
import com.example.dispatchbuddy.data.remote.dto.models.Registration
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.data.remote.dto.models.VerifyUser
import retrofit2.http.*

interface DispatchBuddyAPI {

    @POST("user/signup")
    suspend fun registerUser(@Body registration: Registration): GenericResponse<UserProfile>

    @POST("user/verify")
    suspend fun verifyUser(@Body verifyUser: VerifyUser): GenericResponse<UserProfile>

    @POST("user/validate")
    suspend fun validateUser(@Body email: String): GenericResponse<String>

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("oauth/token")
    suspend fun loginUser(
        @Field("username") username: String,
        @Field("password") password: String,
        @Query("grant_type") grant_type : String
    ) : GenericResponse<LoginResponse>
}