package com.example.dispatchbuddy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponseContent

/**
 * Future implementation, to use paging 3 library with Room database
 * */
@Database(entities = [AllUserRequestResponseContent::class], version = 1, exportSchema = false)
abstract class DispatchBuddyDB: RoomDatabase() {
    abstract val dao: DispatchBuddyDao
}