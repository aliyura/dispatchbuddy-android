package com.example.dispatchbuddy.data.repository

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.apiCall
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.common.network.IAppDispatchers
import com.example.dispatchbuddy.data.remote.dto.models.LoginResponse
import com.example.dispatchbuddy.data.remote.dto.models.Registration
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.data.remote.dto.models.VerifyUser
import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import com.example.dispatchbuddy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val api: DispatchBuddyAPI,
//    private val appDispatchers: IAppDispatchers
) : AuthRepository {

    override suspend fun registerUser(registration: Registration): Flow<Resource<GenericResponse<UserProfile>>> =
        flow {
            emit(
                apiCall {
                    api.registerUser(registration)
                }
            )
        }

    override suspend fun verifyUser(verifyUser: VerifyUser): Flow<Resource<GenericResponse<UserProfile>>> =
        flow {
            emit(
                apiCall{
                    api.verifyUser(verifyUser)
                }
            )
        }

    override suspend fun validateUser(email: String): Flow<Resource<GenericResponse<String>>> =
        flow {
            emit(
                apiCall {
                    api.validateUser(email)
                }
            )
        }

    override suspend fun loginUser(
        username: String,
        password: String,
        grant_type: String
    ): Flow<Resource<GenericResponse<LoginResponse>>> =
        flow {
            emit(
                apiCall {
                    api.loginUser(username, password, grant_type)
                }
            )
        }

}