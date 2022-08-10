package com.example.dispatchbuddy.domain.usecases.riderUseCases

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponse
import com.example.dispatchbuddy.domain.repository.RiderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUserRequestUseCases @Inject constructor(val repository: RiderRepository) {
    suspend operator fun invoke(page: Int, token: String): Flow<Resource<GenericResponse<AllUserRequestResponse>>> =
        repository.getAllUserRequest(page = page, token = token)
}