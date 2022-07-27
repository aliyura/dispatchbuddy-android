package com.example.dispatchbuddy.data.repository

import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import com.example.dispatchbuddy.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    val api: DispatchBuddyAPI
): UserRepository {

}