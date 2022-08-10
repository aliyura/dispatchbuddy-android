package com.example.dispatchbuddy.domain.usecases.riderUseCases

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.userRequestStatusModel.RejectUserRideModel
import com.example.dispatchbuddy.data.remote.dto.models.userRequestStatusModel.UserRequestStatusResponse
import com.example.dispatchbuddy.domain.repository.RiderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RejectUserRequestUseCase @Inject constructor(val repository: RiderRepository) {
    suspend operator fun invoke(rejectUserRequest: RejectUserRideModel, token: String): Flow<Resource<GenericResponse<UserRequestStatusResponse>>> =
        repository.rejectUserRequest( rejectUserRequest = rejectUserRequest, token = token)
}