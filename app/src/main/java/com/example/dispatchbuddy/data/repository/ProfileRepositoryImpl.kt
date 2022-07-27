package com.example.dispatchbuddy.data.repository

import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import com.example.dispatchbuddy.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    val api: DispatchBuddyAPI
): ProfileRepository {

}