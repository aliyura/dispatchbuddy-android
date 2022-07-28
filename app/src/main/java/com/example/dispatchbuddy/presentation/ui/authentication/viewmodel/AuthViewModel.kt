package com.example.dispatchbuddy.presentation.ui.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.ChangePassword
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.domain.usecases.authUseCases.ChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase
): ViewModel() {
    private val _changePasswordResponse = MutableStateFlow<Resource<GenericResponse<UserProfile>>?>(null)
    val changePasswordResponse: StateFlow<Resource<GenericResponse<UserProfile>>?> get() = _changePasswordResponse

    fun changePassword(changePassword: ChangePassword){
        viewModelScope.launch {
            changePasswordUseCase(changePassword = changePassword).collect{
                _changePasswordResponse.value = it
            }
        }
    }
}