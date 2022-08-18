package com.example.dispatchbuddy.presentation.ui.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.ChangePassword
import com.example.dispatchbuddy.data.remote.dto.models.UpdateProfile
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.domain.usecases.profileUseCases.EditProfileUsesCase
import com.example.dispatchbuddy.domain.usecases.profileUseCases.ResetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val editProfileUsesCase: EditProfileUsesCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
): ViewModel() {
    private val _editProfileResponse = MutableStateFlow<Resource<GenericResponse<UserProfile>>?>(null)
    val editProfileResponse: StateFlow<Resource<GenericResponse<UserProfile>>?> get() = _editProfileResponse

    private val _resetPasswordResponse = MutableStateFlow<Resource<GenericResponse<UserProfile>>?>(null)
    val resetPasswordResponse: StateFlow<Resource<GenericResponse<UserProfile>>?> get() = _resetPasswordResponse

    fun updateProfile(updateProfile: UpdateProfile, token: String){
        viewModelScope.launch {
            editProfileUsesCase(updateProfile = updateProfile, token = token).collect{
                _editProfileResponse.value = it
            }
        }
    }
    fun resetUserPassword(resetPassword: ChangePassword){
        viewModelScope.launch {
            resetPasswordUseCase(resetPassword = resetPassword).collect{
                _resetPasswordResponse.value = it
            }
        }
    }
}