package com.example.dispatchbuddy.common.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.dispatchbuddy.common.Constants.STARTING_PAGE
import com.example.dispatchbuddy.data.local.DispatchBuddyDB
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponseContent
import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import retrofit2.HttpException
import java.io.IOException

/**
 * Future implementation, to use paging 3 library with Room database
 * */

@OptIn(ExperimentalPagingApi::class)
class DispatchBuddyRemoteMediator(
    private val api: DispatchBuddyAPI,
    private val token: String,
    private val database: DispatchBuddyDB,
): RemoteMediator<Int, AllUserRequestResponseContent>() {
    private val requestDao = database.dao
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AllUserRequestResponseContent>
    ): MediatorResult {
        return try {

            val loadKey = when (loadType) {
                REFRESH -> STARTING_PAGE
                PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    lastItem.roomDbId
                }
            }
            val request = loadKey?.let { api.pagingGetAllUserRequest(it, token) }
            val data = request?.payload?.allUserRequestResponseContent
            val filteredData = data?.filter { it.status.contains("CO") }
            database.withTransaction {
                if (loadType == REFRESH) {
                    requestDao.deleteAllRequest()
                }
                if (filteredData != null) {
                    requestDao.insertRequests(filteredData)
                }
            }
            MediatorResult.Success(
                endOfPaginationReached = false
            )
        }catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
}