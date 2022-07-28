package com.example.dispatchbuddy.domain.usecases.authUseCases

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.ChangePassword
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(val repository: AuthRepository) {
    suspend operator fun invoke(changePassword: ChangePassword): Flow<Resource<GenericResponse<UserProfile>>> =
        repository.changePassword(changePassword = changePassword)
}