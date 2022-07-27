package com.example.dispatchbuddy.data.repository

import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import com.example.dispatchbuddy.domain.repository.RiderRepository
import javax.inject.Inject

class RiderRepositoryImpl@Inject constructor(
    val api: DispatchBuddyAPI
): RiderRepository {

}