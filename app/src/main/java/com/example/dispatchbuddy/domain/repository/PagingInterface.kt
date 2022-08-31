package com.example.dispatchbuddy.domain.repository

import androidx.paging.PagingData
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponseContent
import kotlinx.coroutines.flow.Flow

/**
 * Future implementation, to use paging 3 library with Room database
 * */
interface PagingInterface {
    suspend fun getPagingRequestsDB(page: Int, token: String): Flow<PagingData<AllUserRequestResponseContent>>
}