package com.example.dispatchbuddy.domain.usecases.authUseCases

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.Registration
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(val repository: AuthRepository) {

    suspend operator fun invoke (registration: Registration) : Flow<Resource<GenericResponse<UserProfile>>> =
        repository.registerUser(registration)
}