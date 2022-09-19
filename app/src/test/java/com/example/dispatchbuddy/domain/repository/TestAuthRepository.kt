package com.example.dispatchbuddy.domain.repository

import com.example.dispatchbuddy.Constants.userProfileTest
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestAuthRepository: AuthRepository {
    private var shouldReturnNetworkError = false
    var resourceMessage = ""
    var resourceError = ""


    override suspend fun registerUser(registration: Registration): Flow<Resource<GenericResponse<String>>> = flow {
        emit(Resource.Loading("Loading..."))
        if (shouldReturnNetworkError){
            emit(Resource.Error(400,resourceError))
        }else{
            emit(Resource.Success(GenericResponse(
                message = resourceMessage,
                success = true,
                payload = "Registration successful, OTP send to your email"
            )))
        }
    }

    override suspend fun verifyUser(verifyUser: VerifyUser): Flow<Resource<GenericResponse<UserProfile>>> =flow {
        emit(Resource.Loading("Loading..."))
        if (shouldReturnNetworkError){
            emit(Resource.Error(400,resourceError))
        }else{
            emit(Resource.Success(
                GenericResponse(
                message = resourceMessage,
                success = true,
                payload = UserProfile(
                    accountType = userProfileTest.accountType,
                    authProvider = userProfileTest.authProvider,
                    city = userProfileTest.city,
                    country = userProfileTest.country,
                    createdDate = userProfileTest.createdDate,
                    dp = userProfileTest.dp,
                    dateOfBirth = userProfileTest.dateOfBirth,
                    email = verifyUser.username,
                    gender = userProfileTest.gender,
                    id = userProfileTest.id,
                    isEnabled = userProfileTest.isEnabled,
                    lastLoginDate = userProfileTest.lastLoginDate,
                    lastModifiedDate = userProfileTest.lastModifiedDate,
                    name = userProfileTest.name,
                    password = userProfileTest.password,
                    phoneNumber = userProfileTest.phoneNumber,
                    role = userProfileTest.role,
                    status = userProfileTest.status,
                    thirdPartyToken = userProfileTest.thirdPartyToken,
                    updatedDate = userProfileTest.updatedDate,
                    uuid = userProfileTest.uuid
                )
            )
            ))
        }
    }

    override suspend fun validateUser(email: String): Flow<Resource<GenericResponse<String>>> {
        TODO("Not yet implemented")
    }

    override suspend fun loginUser(
        username: String,
        password: String,
        grant_type: String
    ): Flow<Resource<LoginResponse>> = flow {
        emit(Resource.Loading("Loading..."))
        if (shouldReturnNetworkError){
            emit(Resource.Error(400,resourceError))
        }else{
            emit(Resource.Success(
                LoginResponse(
                    access_token = "tygdhrhk",
                    accountType = "Dispatcher",
                    city = "Lagos",
                    dp = "http://www.image.com",
                    email = "iniyealakeret1@gmail.com",
                    gender = "Male",
                    id = "123er45",
                    isEnabled = true,
                    jti = "123er45",
                    name = "Emmanuel",
                    phoneNumber="08101553210",
                    refresh_token = "tygdhrhk",
                    role = "",
                    scope = "",
                    status = "",
                    token_type = "",
                    uuid = ""
                )))
        }
    }

    override suspend fun changePassword(changePassword: ChangePassword): Flow<Resource<GenericResponse<UserProfile>>> {
        TODO("Not yet implemented")
    }

    fun shouldReturnError(value: Boolean, errorMessage: String) {
        shouldReturnNetworkError = value
        resourceError = errorMessage
    }
}