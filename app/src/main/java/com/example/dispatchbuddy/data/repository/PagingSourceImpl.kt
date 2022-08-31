package com.example.dispatchbuddy.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.dispatchbuddy.common.Constants
import com.example.dispatchbuddy.common.pagination.DispatchBuddyRemoteMediator
import com.example.dispatchbuddy.data.local.DispatchBuddyDB
import com.example.dispatchbuddy.data.local.DispatchBuddyDao
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponseContent
import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import com.example.dispatchbuddy.domain.repository.PagingInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Future implementation, to use paging 3 library with Room database
 * */
class PagingSourceImpl @Inject constructor(
    val api: DispatchBuddyAPI,
    private val database: DispatchBuddyDB
): PagingInterface {
    private val requestDao: DispatchBuddyDao = database.dao
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPagingRequestsDB(
        page: Int,
        token: String
    ): Flow<PagingData<AllUserRequestResponseContent>> =
        Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = DispatchBuddyRemoteMediator(
                api, token, database
            )
            ,
            pagingSourceFactory = { requestDao.getAllRequestsDb() }
        ).flow
}