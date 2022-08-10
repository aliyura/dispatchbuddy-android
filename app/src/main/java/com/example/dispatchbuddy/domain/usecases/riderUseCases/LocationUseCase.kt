package com.example.dispatchbuddy.domain.usecases.riderUseCases

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.CoveredLocationsResponse
import com.example.dispatchbuddy.data.remote.dto.models.Locations
import com.example.dispatchbuddy.domain.repository.RiderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationUseCase @Inject constructor(val repository: RiderRepository) {
    suspend operator fun invoke(locations :Locations, token: String) : Flow<Resource<GenericResponse<CoveredLocationsResponse>>> =
        repository.addCoveredLocations(locations, token)

}