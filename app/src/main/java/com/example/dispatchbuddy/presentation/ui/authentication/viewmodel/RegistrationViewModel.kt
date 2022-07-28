package com.example.dispatchbuddy.presentation.ui.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.Registration
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val repository: AuthRepository): ViewModel() {

//    private var _registrationData = MutableLiveData<Resource<GenericResponse<UserProfile>>>()
//    val registrationData : LiveData<Resource<GenericResponse<UserProfile>>> get() = _registrationData
    private val mutableStateFlow = MutableStateFlow<Resource<GenericResponse<UserProfile>>?>(null)
    val stateFlow: StateFlow<Resource<GenericResponse<UserProfile>>?> get() = mutableStateFlow

    fun registerUser(registration: Registration){
        viewModelScope.launch {
            repository.registerUser(registration).collect{
                mutableStateFlow.value = it
            }
        }
    }
}