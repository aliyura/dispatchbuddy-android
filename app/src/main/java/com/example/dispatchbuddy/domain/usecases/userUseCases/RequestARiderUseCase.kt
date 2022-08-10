package com.example.dispatchbuddy.domain.usecases.userUseCases

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel.RequestRiderResponse
import com.example.dispatchbuddy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RequestARiderUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(
        page: Int,
        pickup: String,
        destination: String
    ): Flow<Resource<GenericResponse<RequestRiderResponse>>> =
        repository.requestARider(page = page, pickup = pickup, destination = destination)

}