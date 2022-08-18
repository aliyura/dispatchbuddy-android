package com.example.dispatchbuddy.domain.repository

import androidx.paging.PagingData
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.CoveredLocationsResponse
import com.example.dispatchbuddy.data.remote.dto.models.Locations
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponse
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponseContent
import com.example.dispatchbuddy.data.remote.dto.models.userRequestStatusModel.RejectUserRideModel
import com.example.dispatchbuddy.data.remote.dto.models.userRequestStatusModel.UserRequestStatusResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface RiderRepository {
    suspend fun uploadImage(dp: MultipartBody.Part, token: String): Flow<Resource<GenericResponse<UserProfile>>>
    suspend fun getUser(id: String, token: String): Flow<Resource<GenericResponse<UserProfile>>>
    suspend fun addCoveredLocations(locations: Locations, token: String) : Flow<Resource<GenericResponse<CoveredLocationsResponse>>>
    suspend fun getAllUserRequest(page: Int, token: String): Flow<Resource<GenericResponse<AllUserRequestResponse>>>
    suspend fun rejectUserRequest(rejectUserRequest: RejectUserRideModel, token: String): Flow<Resource<GenericResponse<UserRequestStatusResponse>>>
    suspend fun acceptUserRequest(id: String, token: String): Flow<Resource<GenericResponse<UserRequestStatusResponse>>>
    suspend fun closeUserRequest(id: String, token: String): Flow<Resource<GenericResponse<UserRequestStatusResponse>>>
    suspend fun getPagingRequests(page: Int, token: String): Flow<PagingData<AllUserRequestResponseContent>>
}