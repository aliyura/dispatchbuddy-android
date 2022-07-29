package com.example.dispatchbuddy.domain.usecases.profileUseCases

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.ChangePassword
import com.example.dispatchbuddy.data.remote.dto.models.UpdateProfile
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditProfileUsesCase @Inject constructor(val repository: ProfileRepository) {
    suspend operator fun invoke(updateProfile: UpdateProfile, token: String): Flow<Resource<GenericResponse<UserProfile>>> =
        repository.updateProfile(updateProfile = updateProfile, token = token)
}