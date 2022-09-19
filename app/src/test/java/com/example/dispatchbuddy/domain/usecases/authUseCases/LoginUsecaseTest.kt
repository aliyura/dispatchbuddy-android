package com.example.dispatchbuddy.domain.usecases.authUseCases

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.data.remote.dto.models.LoginResponse
import com.example.dispatchbuddy.domain.repository.TestAuthRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LoginUsecaseTest {
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var fakeAuthRepository: TestAuthRepository
    private val username = "iniyealakeret1@gmail.com"
    private val password = "Emma@1234"
    private val grant_type = "EMAIL"

    @Before
    fun setUp(){
        fakeAuthRepository = TestAuthRepository()
        loginUseCase = LoginUseCase(fakeAuthRepository)
    }

    @Test
    fun `Flow emits loading state`() = runBlocking{
        val loginRequest = loginUseCase(username, password, grant_type).first()
        assertThat(loginRequest).isEqualTo(Resource.Loading<Any>("Loading..."))
    }
    @Test
    fun `login with incorrect details and return an error message`() = runBlocking {
        val errorMessage = "Incorrect email and password"
        fakeAuthRepository.shouldReturnError(true, errorMessage)
        val loginRequest = loginUseCase(username, password, grant_type).last()
        assertThat(loginRequest).isEqualTo(Resource.Error(400, errorMessage))
    }
    @Test
    fun `login with correct details`() = runBlocking {
        val loginRequest = loginUseCase(username, password, grant_type).last()
        val loginResponse = LoginResponse(
            access_token = "tygdhrhk",
            accountType = "Dispatcher",
            city = "Lagos",
            dp = "http://www.image.com",
            email = username,
            gender = "Male",
            id = "123er45",
            isEnabled = true,
            jti = "123er45",
            name = "Emmanuel",
            phoneNumber = "08101553210",
            refresh_token = "tygdhrhk",
            role = "",
            scope = "",
            status = "",
            token_type = "",
            uuid = ""
        )
        assertThat(loginRequest).isEqualTo(Resource.Success(loginResponse))
    }

}