package com.example.dispatchbuddy.domain.usecases.paginationUseCase

import androidx.paging.PagingData
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponseContent
import com.example.dispatchbuddy.domain.repository.PagingInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PaginationUseCase  @Inject constructor(val repository: PagingInterface){
    suspend operator fun invoke(page: Int, token: String): Flow<PagingData<AllUserRequestResponseContent>> =
        repository.getPagingRequestsDB(page, token)
}