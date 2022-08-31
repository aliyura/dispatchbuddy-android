package com.example.dispatchbuddy.common.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponseContent
import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import javax.inject.Inject


class PaginationSource @Inject constructor(
    private val api: DispatchBuddyAPI,
    private val token: String
    ): PagingSource<Int, AllUserRequestResponseContent>() {

    override fun getRefreshKey(state: PagingState<Int, AllUserRequestResponseContent>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AllUserRequestResponseContent> {
        return try {
            val pageNumber = params.key ?: 0
            val response = api.pagingGetAllUserRequest(pageNumber, token)
            val data = response.payload.allUserRequestResponseContent
            val filteredData = data.filter { !it.status.contains("CO") }
            LoadResult.Page(
                data = filteredData,
                prevKey = null,
                nextKey = if (data.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
