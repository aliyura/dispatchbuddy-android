package com.example.dispatchbuddy.domain.repository

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.ChangePassword
import com.example.dispatchbuddy.data.remote.dto.models.UpdateProfile
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun updateProfile(updateProfile: UpdateProfile, token: String): Flow<Resource<GenericResponse<UserProfile>>>
    suspend fun resetPassword(resetPassword: ChangePassword): Flow<Resource<GenericResponse<UserProfile>>>
}