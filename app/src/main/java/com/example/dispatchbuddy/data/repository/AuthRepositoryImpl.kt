package com.example.dispatchbuddy.data.repository

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.common.network.apiCall
import com.example.dispatchbuddy.data.remote.dto.models.*
import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import com.example.dispatchbuddy.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Credentials

class AuthRepositoryImpl(
    private val api: DispatchBuddyAPI,
) : AuthRepository {

    override suspend fun registerUser(registration: Registration): Flow<Resource<GenericResponse<UserProfile>>> =
        flow {
            emit(Resource.Loading("Loading"))
            emit(
                apiCall {
                    api.registerUser(registration)
                }
            )
        }

    override suspend fun verifyUser(verifyUser: VerifyUser): Flow<Resource<GenericResponse<UserProfile>>> =
        flow {
            emit(Resource.Loading("Loading"))
            emit(
                apiCall {
                    api.verifyUser(verifyUser)
                }
            )
        }

    override suspend fun validateUser(email: String): Flow<Resource<GenericResponse<String>>> =
        flow {
            emit(Resource.Loading("Loading"))
            emit(
                apiCall {
                    api.validateUser(email)
                }
            )
        }

    override suspend fun loginUser(
        authorizationHeader: String,
        username: String,
        password: String,
        grant_type: String
    ): Flow<Resource<GenericResponse<LoginResponse>>> =
        flow {
            emit(Resource.Loading("Loading"))
            emit(
                apiCall {
                    api.loginUser(username=username, password = password, grant_type = grant_type, Authorization = authorizationHeader)
                }
            )
        }

    override suspend fun changePassword(changePassword: ChangePassword): Flow<Resource<GenericResponse<UserProfile>>> =
        flow {
            emit(Resource.Loading("Loading"))
            emit(apiCall { api.changePassword(changePassword) })
        }.flowOn(Dispatchers.IO)


}