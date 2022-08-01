package com.example.dispatchbuddy.presentation.ui.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.Registration
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.domain.usecases.authUseCases.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val registrationUseCase: RegisterUseCase): ViewModel() {

    private val mutableStateFlow = MutableStateFlow<Resource<GenericResponse<UserProfile>>?>(null)
    val stateFlow: StateFlow<Resource<GenericResponse<UserProfile>>?> get() = mutableStateFlow

    fun registerUser(registration: Registration){
        viewModelScope.launch {
            registrationUseCase(registration).collect{
                mutableStateFlow.value = it
            }
        }
    }
}