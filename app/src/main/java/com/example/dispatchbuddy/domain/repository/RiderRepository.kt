package com.example.dispatchbuddy.domain.repository

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface RiderRepository {
    suspend fun uploadImage(dp: MultipartBody.Part, token: String): Flow<Resource<GenericResponse<UserProfile>>>
    suspend fun getUser(id: String, token: String): Flow<Resource<GenericResponse<UserProfile>>>
}