package com.example.dispatchbuddy.data.repository

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.common.network.apiCall
import com.example.dispatchbuddy.data.remote.dto.models.UpdateProfile
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import com.example.dispatchbuddy.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    val api: DispatchBuddyAPI
): ProfileRepository {
    override suspend fun updateProfile(
        updateProfile: UpdateProfile,
        token: String
    ): Flow<Resource<GenericResponse<UserProfile>>>  = flow {
        emit(apiCall { api.updateProfile(update = updateProfile, token = token) })
    }.flowOn(Dispatchers.IO)

}