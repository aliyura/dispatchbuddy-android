package com.example.dispatchbuddy.domain.usecases.authUseCases

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.data.remote.dto.models.VerifyUser
import com.example.dispatchbuddy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SmsVerificationUseCase @Inject constructor(val repository: AuthRepository) {
    suspend operator fun invoke(verifyUser: VerifyUser): Flow<Resource<GenericResponse<UserProfile>>> =
        repository.verifyUser(verifyUser)

    suspend operator fun invoke(email: String): Flow<Resource<GenericResponse<String>>> =
        repository.validateUser(email)
}