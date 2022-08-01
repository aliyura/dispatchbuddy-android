package com.example.dispatchbuddy.presentation.ui.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.LoginResponse
import com.example.dispatchbuddy.domain.repository.AuthRepository
import com.example.dispatchbuddy.domain.usecases.authUseCases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Credentials
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val loginUseCase: LoginUseCase) : ViewModel() {
    private var _loginResponse = MutableStateFlow<Resource<LoginResponse>?>(null)
    val loginResponse: StateFlow<Resource<LoginResponse>?> get() = _loginResponse


    fun loginUser(username: String, password: String, grant_type: String) {
        viewModelScope.launch {
            loginUseCase(username, password, grant_type).collect {
                _loginResponse.value = it
            }
        }
    }
}