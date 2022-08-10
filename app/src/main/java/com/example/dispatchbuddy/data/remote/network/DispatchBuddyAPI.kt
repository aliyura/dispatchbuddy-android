package com.example.dispatchbuddy.data.remote.network

import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.CoveredLocationsResponse
import com.example.dispatchbuddy.data.remote.dto.models.LoginResponse
import com.example.dispatchbuddy.data.remote.dto.models.ChangePassword
import com.example.dispatchbuddy.data.remote.dto.models.Registration
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.data.remote.dto.models.VerifyUser
import com.example.dispatchbuddy.data.remote.dto.models.*
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponse
import com.example.dispatchbuddy.data.remote.dto.models.userRequestStatusModel.RejectUserRideModel
import com.example.dispatchbuddy.data.remote.dto.models.userRequestStatusModel.UserRequestStatusResponse
import com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel.ContactRiderModel
import com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel.ContactRiderResponse
import com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel.RequestRiderResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface DispatchBuddyAPI {

    @POST("user/signup")
    suspend fun registerUser(@Body registration: Registration): GenericResponse<UserProfile>

    @POST("user/verify")
    suspend fun verifyUser(@Body verifyUser: VerifyUser): GenericResponse<UserProfile>

    @POST("user/validate")
    suspend fun validateUser(@Body email: String): GenericResponse<String>

    @Headers(
        "Content-Type: application/x-www-form-urlencoded",
        "accept-encoding: gzip, deflate, br"
    )
    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun loginUser(
        @Header("Authorization") credentials: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grant_type: String
    ): LoginResponse

    @POST("user/password-reset")
    suspend fun changePassword(@Body changePassword: ChangePassword): GenericResponse<UserProfile>

    @PUT("user/update")
    suspend fun updateProfile(
        @Body update: UpdateProfile,
        @Header("Authorization") token: String
    ): GenericResponse<UserProfile>

    @Multipart
    @PUT("user/update-dp")
    suspend fun uploadImage(
        @Part dp: MultipartBody.Part,
        @Header("Authorization") token: String
    ): GenericResponse<UserProfile>

    @GET("user/get-by-id/{id}")
    suspend fun getUser(@Path("id") id: String, @Header("Authorization") token: String): GenericResponse<UserProfile>

    @GET("rider/search")
    suspend fun requestARider(
        @Query("page") page: Int,
        @Query("pickup") pickup: String,
        @Query("destination") destination: String,
    ): GenericResponse<RequestRiderResponse>

    @POST("rider/add-locations")
    suspend fun addCoveredLocations(
        @Body locations: Locations,
        @Header("Authorization") token: String
    ): GenericResponse<CoveredLocationsResponse>

    @POST("rider/request")
    suspend fun contactARider(@Body contactRiderModel: ContactRiderModel): GenericResponse<ContactRiderResponse>

    @GET("rider/requests")
    suspend fun getAllUserRequest(
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ): GenericResponse<AllUserRequestResponse>

    @POST("rider/reject-request")
    suspend fun rejectUserRequest(
        @Body rejectUserRequest: RejectUserRideModel,
        @Header("Authorization") token: String
    ): GenericResponse<UserRequestStatusResponse>

    @POST("rider/accept-request/{id}")
    suspend fun acceptUserRequest(
        @Path("id") id: String, @Header("Authorization") token: String
    ): GenericResponse<UserRequestStatusResponse>

    @POST("rider/close-request/{id}")
    suspend fun closeUserRequest(
        @Path("id") id: String, @Header("Authorization") token: String
    ): GenericResponse<UserRequestStatusResponse>


}