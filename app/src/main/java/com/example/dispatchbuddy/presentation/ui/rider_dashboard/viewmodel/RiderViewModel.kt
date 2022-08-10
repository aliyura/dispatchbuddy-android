package com.example.dispatchbuddy.presentation.ui.rider_dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponse
import com.example.dispatchbuddy.data.remote.dto.models.userRequestStatusModel.RejectUserRideModel
import com.example.dispatchbuddy.data.remote.dto.models.userRequestStatusModel.UserRequestStatusResponse
import com.example.dispatchbuddy.domain.usecases.riderUseCases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class RiderViewModel @Inject constructor(
    private val imageUseCase: UploadImageUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getAllUserRequestUseCases: GetAllUserRequestUseCases,
    private val rejectUserRequestUseCase: RejectUserRequestUseCase,
    private val acceptUserUseCase: AcceptUserUseCase,
    private val closeUserRequestUseCase: CloseUserRequestUseCase
): ViewModel() {
    private val _imageUploadResponse = MutableStateFlow<Resource<GenericResponse<UserProfile>>?>(null)
    val imageUploadResponse: StateFlow<Resource<GenericResponse<UserProfile>>?> get() = _imageUploadResponse

    private val _getUser = MutableStateFlow<Resource<GenericResponse<UserProfile>>?>(null)
    val getUser: StateFlow<Resource<GenericResponse<UserProfile>>?> get() = _getUser

    private val _getAllUserRequestResponse = MutableStateFlow<Resource<GenericResponse<AllUserRequestResponse>>?>(null)
    val getAllUserRequestResponse: StateFlow<Resource<GenericResponse<AllUserRequestResponse>>?> get() = _getAllUserRequestResponse

    private val _rejectUserRequestResponse = MutableStateFlow<Resource<GenericResponse<UserRequestStatusResponse>>?>(null)
    val rejectUserRequestResponse: StateFlow<Resource<GenericResponse<UserRequestStatusResponse>>?> get() = _rejectUserRequestResponse

    private val _acceptUserRequestResponse = MutableStateFlow<Resource<GenericResponse<UserRequestStatusResponse>>?>(null)
    val acceptUserRequestResponse: StateFlow<Resource<GenericResponse<UserRequestStatusResponse>>?> get() = _acceptUserRequestResponse

    private val _closeUserRequestResponse = MutableStateFlow<Resource<GenericResponse<UserRequestStatusResponse>>?>(null)
    val closeUserRequestResponse: StateFlow<Resource<GenericResponse<UserRequestStatusResponse>>?> get() = _closeUserRequestResponse

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

    fun getAllUserRequest(page: Int, token: String){
        viewModelScope.launch {
            getAllUserRequestUseCases(page = page, token = token).collect{
                _getAllUserRequestResponse.value = it
            }
        }
    }

    fun rejectUserRequest(rejectUserRideModel: RejectUserRideModel, token: String){
        viewModelScope.launch {
            rejectUserRequestUseCase(rejectUserRequest = rejectUserRideModel, token = token).collect{
                _rejectUserRequestResponse.value = it
            }
        }
    }

    fun acceptUserRequest(id: String, token: String){
        viewModelScope.launch {
            acceptUserUseCase(id = id, token = token).collect{
                _acceptUserRequestResponse.value = it
            }
        }
    }

    fun closeUserRequest(id: String, token: String){
        viewModelScope.launch {
            closeUserRequestUseCase(id = id, token = token).collect{
                _closeUserRequestResponse.value = it
            }
        }
    }

}