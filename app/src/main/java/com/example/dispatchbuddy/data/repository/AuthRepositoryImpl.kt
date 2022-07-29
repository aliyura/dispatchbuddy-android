package com.example.dispatchbuddy.data.repository

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.apiCall
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.common.network.IAppDispatchers
import com.example.dispatchbuddy.data.remote.dto.models.ChangePassword
import com.example.dispatchbuddy.data.remote.dto.models.Registration
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.data.remote.dto.models.VerifyUser
import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import com.example.dispatchbuddy.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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

    override suspend fun changePassword(changePassword: ChangePassword): Flow<Resource<GenericResponse<UserProfile>>> = flow {
        emit(Resource.Loading(""))
        emit(apiCall{ api.changePassword(changePassword) })
    }.flowOn(Dispatchers.IO)

}