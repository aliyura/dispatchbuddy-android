package com.example.dispatchbuddy.domain.repository

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel.ContactRiderModel
import com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel.ContactRiderResponse
import com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel.RequestRiderResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository  {
    suspend fun requestARider(page: Int, pickup: String, destination: String): Flow<Resource<GenericResponse<RequestRiderResponse>>>
    suspend fun contactARider(contactRiderModel: ContactRiderModel): Flow<Resource<GenericResponse<ContactRiderResponse>>>
}