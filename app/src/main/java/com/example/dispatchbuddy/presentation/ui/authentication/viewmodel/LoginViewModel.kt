package com.example.dispatchbuddy.presentation.ui.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.LoginResponse
import com.example.dispatchbuddy.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val repository: AuthRepository) : ViewModel() {
    private var _loginResponse = MutableStateFlow<Resource<GenericResponse<LoginResponse>>?>(null)
    val loginResponse: StateFlow<Resource<GenericResponse<LoginResponse>>?> get() = _loginResponse


    fun loginUser(username: String, password: String, grant_type: String) {
        viewModelScope.launch {
            repository.loginUser(username, password, grant_type).collect {
                _loginResponse.value = it
            }
        }
    }
}