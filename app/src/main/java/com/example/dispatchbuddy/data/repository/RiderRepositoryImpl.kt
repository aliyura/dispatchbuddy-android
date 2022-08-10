package com.example.dispatchbuddy.data.repository

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.common.network.apiCall
import com.example.dispatchbuddy.data.remote.dto.CoveredLocationsResponse
import com.example.dispatchbuddy.data.remote.dto.models.Locations
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponse
import com.example.dispatchbuddy.data.remote.dto.models.userRequestStatusModel.RejectUserRideModel
import com.example.dispatchbuddy.data.remote.dto.models.userRequestStatusModel.UserRequestStatusResponse
import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import com.example.dispatchbuddy.domain.repository.RiderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class RiderRepositoryImpl@Inject constructor(
    val api: DispatchBuddyAPI
): RiderRepository {
    override suspend fun uploadImage(
        dp: MultipartBody.Part,
        token: String
    ): Flow<Resource<GenericResponse<UserProfile>>> = flow{
        emit(Resource.Loading("Loading"))
        emit(apiCall { api.uploadImage(dp = dp, token = token) })
    }

    override suspend fun getUser(
        id: String,
        token: String
    ): Flow<Resource<GenericResponse<UserProfile>>>  = flow {
        emit(Resource.Loading("Loading"))
        emit(apiCall { api.getUser(id = id, token = token) })
    }

    override suspend fun addCoveredLocations(locations: Locations, token: String): Flow<Resource<GenericResponse<CoveredLocationsResponse>>> =
        flow {
            emit(Resource.Loading("Loading"))
            emit(apiCall { api.addCoveredLocations(locations, token) })
        }

    override suspend fun getAllUserRequest(
        page: Int,
        token: String
    ): Flow<Resource<GenericResponse<AllUserRequestResponse>>> = flow{
        emit(Resource.Loading("Loading"))
        emit(apiCall { api.getAllUserRequest(page = page, token = token) })
    }

    override suspend fun rejectUserRequest(
        rejectUserRequest: RejectUserRideModel,
        token: String
    ): Flow<Resource<GenericResponse<UserRequestStatusResponse>>> = flow {
        emit(Resource.Loading("Loading"))
        emit(apiCall { api.rejectUserRequest(rejectUserRequest = rejectUserRequest, token = token)})
        }

    override suspend fun acceptUserRequest(
        id: String,
        token: String
    ): Flow<Resource<GenericResponse<UserRequestStatusResponse>>> = flow {
        emit(Resource.Loading("Loading"))
        emit(apiCall { api.acceptUserRequest(id = id, token = token) })
    }

    override suspend fun closeUserRequest(
        id: String,
        token: String
    ): Flow<Resource<GenericResponse<UserRequestStatusResponse>>> = flow {
        emit(Resource.Loading("Loading"))
        emit(apiCall { api.closeUserRequest(id = id, token = token) })
    }
}