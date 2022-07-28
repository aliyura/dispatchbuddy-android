package com.example.dispatchbuddy.domain.repository

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.Registration
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.data.remote.dto.models.VerifyUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun registerUser(registration: Registration) : Flow<Resource<GenericResponse<UserProfile>>>
    suspend fun verifyUser(verifyUser: VerifyUser) : Flow<Resource<GenericResponse<UserProfile>>>
    suspend fun validateUser(email: String) : Flow<Resource<GenericResponse<String>>>
}