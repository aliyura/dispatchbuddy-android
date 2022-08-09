package com.example.dispatchbuddy.data.repository

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.common.network.apiCall
import com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel.ContactRiderModel
import com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel.ContactRiderResponse
import com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel.RequestRiderResponse
import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import com.example.dispatchbuddy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    val api: DispatchBuddyAPI
): UserRepository {
    override suspend fun requestARider(
        page: Int,
        pickup: String,
        destination: String
    ): Flow<Resource<GenericResponse<RequestRiderResponse>>> = flow {
        emit(Resource.Loading("Loading"))
        emit(apiCall { api.requestARider(page = page, pickup = pickup, destination = destination) })
    }

    override suspend fun contactARider(
        contactRiderModel: ContactRiderModel
    ): Flow<Resource<GenericResponse<ContactRiderResponse>>> = flow {
        emit(Resource.Loading("Loading"))
        emit(apiCall { api.contactARider(contactRiderModel = contactRiderModel)})
    }

}