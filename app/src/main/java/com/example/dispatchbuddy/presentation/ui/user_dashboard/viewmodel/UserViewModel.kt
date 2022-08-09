package com.example.dispatchbuddy.presentation.ui.user_dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel.ContactRiderModel
import com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel.ContactRiderResponse
import com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel.RequestRiderResponse
import com.example.dispatchbuddy.domain.usecases.userUseCases.ContactARiderUseCase
import com.example.dispatchbuddy.domain.usecases.userUseCases.RequestARiderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val requestARiderUseCase: RequestARiderUseCase,
    private val contactARiderUseCase: ContactARiderUseCase
): ViewModel() {
    private val _requestARiderResponse = MutableStateFlow<Resource<GenericResponse<RequestRiderResponse>>?>(null)
    val requestARiderResponse : StateFlow<Resource<GenericResponse<RequestRiderResponse>>?> get() = _requestARiderResponse
    private val _contactARiderResponse = MutableStateFlow<Resource<GenericResponse<ContactRiderResponse>>?>(null)
    val contactARiderResponse : StateFlow<Resource<GenericResponse<ContactRiderResponse>>?> get() = _contactARiderResponse

    fun requestARider(page: Int, pickup: String, destination: String){
        viewModelScope.launch {
            requestARiderUseCase(page = page, pickup = pickup, destination = destination).collect{
                _requestARiderResponse.value = it
            }
        }
    }

    fun contactARider(contactRiderModel: ContactRiderModel){
        viewModelScope.launch {
            contactARiderUseCase(contactRiderModel = contactRiderModel).collect{
                _contactARiderResponse.value = it
            }
        }
    }
}