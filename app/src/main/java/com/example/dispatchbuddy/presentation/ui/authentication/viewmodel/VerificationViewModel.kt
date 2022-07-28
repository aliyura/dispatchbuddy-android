package com.example.dispatchbuddy.presentation.ui.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.data.remote.dto.models.VerifyUser
import com.example.dispatchbuddy.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(private val repository: AuthRepository): ViewModel() {

    private val verificationMutableStateFlow = MutableStateFlow<Resource<GenericResponse<UserProfile>>?>(null)
    val verificationStateFlow: StateFlow<Resource<GenericResponse<UserProfile>>?> get() = verificationMutableStateFlow

    private val _validationResponse = MutableStateFlow<Resource<GenericResponse<String>>?>(null)
    val validationResponse : StateFlow<Resource<GenericResponse<String>>?> get() = _validationResponse

    fun verifyUser(verifyUser: VerifyUser) {
        viewModelScope.launch {
            repository.verifyUser(verifyUser).collect{
                verificationMutableStateFlow.value = it
            }
        }
    }

    fun validateUser(email: String) {
        viewModelScope.launch {
            repository.validateUser(email).collect{
                _validationResponse.value = it
            }
        }
    }
}