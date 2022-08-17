//package com.example.dispatchbuddy.common
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponseContent
//import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
//import retrofit2.HttpException
//import java.io.IOException
//import javax.inject.Inject
//
//const val STARTING_PAGE_INDEX = 0
//const val NETWORK_PAGE_SIZE = 10
//
//class PagingDataSource @Inject constructor(
//    private val api: DispatchBuddyAPI,
//    private var page: Int,
//    private val token: String
//    ): PagingSource<Int, AllUserRequestResponseContent>() {
//
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AllUserRequestResponseContent> {
//
//        val position = params.key ?: STARTING_PAGE_INDEX
//        page = position
//        return try {
//            val response = api.testGetAllUserRequest(page, token)
//            LoadResult.Page(
//                    data = response,
//                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
//                    nextKey = if (response.isEmpty()) null else position + 1
//                )
//        }catch (exception: IOException) {
//            return LoadResult.Error(exception)
//        } catch (exception: HttpException) {
//            return LoadResult.Error(exception)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, AllUserRequestResponseContent>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
//    }
//}
