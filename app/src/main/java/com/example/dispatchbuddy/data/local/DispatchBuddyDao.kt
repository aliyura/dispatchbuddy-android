package com.example.dispatchbuddy.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponseContent

/**
 * Future implementation, to use paging 3 library with Room database
 * */
@Dao
interface DispatchBuddyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequests(allUserRequestResponseContent: List<AllUserRequestResponseContent>)

    @Query("SELECT * FROM All_Requests ORDER BY roomDbId DESC")
    fun getAllRequestsDb(): PagingSource<Int, AllUserRequestResponseContent>

    @Query("DELETE FROM All_Requests")
    suspend fun deleteAllRequest()
}