package com.example.dispatchbuddy.domain.usecases.riderUseCases

import androidx.paging.PagingData
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponseContent
import com.example.dispatchbuddy.domain.repository.RiderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeliveriesPagingUseCase @Inject constructor(val repository: RiderRepository) {
    suspend operator fun invoke(page: Int, token: String): Flow<PagingData<AllUserRequestResponseContent>> =
        repository.getDeliveries(page = page, token = token)
}