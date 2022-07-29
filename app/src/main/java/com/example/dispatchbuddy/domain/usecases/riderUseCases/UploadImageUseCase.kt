package com.example.dispatchbuddy.domain.usecases.riderUseCases

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.domain.repository.RiderRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(val repository: RiderRepository) {
    suspend operator fun invoke(dp: MultipartBody.Part, token: String): Flow<Resource<GenericResponse<UserProfile>>> =
        repository.uploadImage(dp = dp, token = token)
}