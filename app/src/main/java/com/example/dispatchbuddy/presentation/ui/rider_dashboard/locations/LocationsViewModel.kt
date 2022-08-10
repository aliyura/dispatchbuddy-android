package com.example.dispatchbuddy.presentation.ui.rider_dashboard.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.CoveredLocationsResponse
import com.example.dispatchbuddy.data.remote.dto.models.Locations
import com.example.dispatchbuddy.domain.usecases.riderUseCases.LocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(private val locationUseCase: LocationUseCase): ViewModel() {
    private var _locationsCoveredResponse = MutableStateFlow<Resource<GenericResponse<CoveredLocationsResponse>>?>(null)
    val locationsCoveredResponse : StateFlow<Resource<GenericResponse<CoveredLocationsResponse>>?> get() = _locationsCoveredResponse

    fun addCoveredLocations(locations : Locations, token : String) {
        viewModelScope.launch {
            locationUseCase(locations, token).collect{
                _locationsCoveredResponse.value = it
            }
        }
    }
}