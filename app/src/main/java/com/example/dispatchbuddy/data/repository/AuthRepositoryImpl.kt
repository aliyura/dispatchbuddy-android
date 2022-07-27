package com.example.dispatchbuddy.data.repository

import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import com.example.dispatchbuddy.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    val api: DispatchBuddyAPI
): AuthRepository {

}