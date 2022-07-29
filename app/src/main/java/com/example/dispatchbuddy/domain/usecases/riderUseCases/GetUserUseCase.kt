package com.example.dispatchbuddy.domain.usecases.riderUseCases

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.domain.repository.RiderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(val repository: RiderRepository) {
    suspend operator fun invoke(id: String, token: String): Flow<Resource<GenericResponse<UserProfile>>> =
        repository.getUser(id = id, token = token)
}