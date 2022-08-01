package com.example.dispatchbuddy.domain.usecases.authUseCases

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.LoginResponse
import com.example.dispatchbuddy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(val repository: AuthRepository) {
    suspend operator fun invoke(
        username: String,
        password: String,
        grant_type: String
    ): Flow<Resource<LoginResponse>> =
        repository.loginUser(username, password, grant_type)
}
