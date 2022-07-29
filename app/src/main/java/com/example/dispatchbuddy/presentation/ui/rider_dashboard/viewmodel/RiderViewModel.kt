package com.example.dispatchbuddy.presentation.ui.rider_dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.domain.usecases.riderUseCases.GetUserUseCase
import com.example.dispatchbuddy.domain.usecases.riderUseCases.UploadImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RiderViewModel @Inject constructor(
    private val imageUseCase: UploadImageUseCase,
    private val getUserUseCase: GetUserUseCase
): ViewModel() {
    private val _imageUploadResponse = MutableStateFlow<Resource<GenericResponse<UserProfile>>?>(null)
    val imageUploadResponse: StateFlow<Resource<GenericResponse<UserProfile>>?> get() = _imageUploadResponse

    private val _getUser = MutableStateFlow<Resource<GenericResponse<UserProfile>>?>(null)
    val getUser: StateFlow<Resource<GenericResponse<UserProfile>>?> get() = _getUser

    fun uploadImage(dp: MultipartBody.Part, token: String){
        viewModelScope.launch {
            imageUseCase(dp = dp, token = token).collect{
                _imageUploadResponse.value = it
            }
        }
    }
    fun getUser(id: String, token: String){
        viewModelScope.launch {
            getUserUseCase(id = id, token = token).collect{
                _getUser.value = it
            }
        }
    }
}