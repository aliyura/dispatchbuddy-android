package com.example.dispatchbuddy.domain.usecases.profileUseCases

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.ChangePassword
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(val repository: ProfileRepository) {
    suspend operator fun invoke(resetPassword: ChangePassword): Flow<Resource<GenericResponse<UserProfile>>> =
        repository.resetPassword(resetPassword = resetPassword)
}