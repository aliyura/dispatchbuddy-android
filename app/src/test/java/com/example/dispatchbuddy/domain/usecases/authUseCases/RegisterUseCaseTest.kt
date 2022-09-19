package com.example.dispatchbuddy.domain.usecases.authUseCases

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.Registration
import com.example.dispatchbuddy.domain.repository.TestAuthRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RegisterUseCaseTest {
    private lateinit var registerUseCase: RegisterUseCase
    private lateinit var fakeAuthRepository: TestAuthRepository
    val authProvider = "token"
    val dateOfBirth = "26/08/1996"
    val email = "iniyealakeret1@gmail.com"
    val name = "Emmanuel Orunimighen"
    val password = "Emma@1234"
    val phoneNumber = "08101553210"
    @Before
    fun setUp() {
        fakeAuthRepository = TestAuthRepository()
        registerUseCase = RegisterUseCase(fakeAuthRepository)
    }
    @Test
    fun `flow emitting Loading Result first`() = runBlocking {
        val registrationRequest = Registration(authProvider, dateOfBirth, email, name, password, phoneNumber)
        val registrationResponse = registerUseCase(registrationRequest).first()
        assertThat(registrationResponse).isEqualTo(Resource.Loading<Any>("Loading..."))
    }
    @Test
    fun `Test to show registration success`() = runBlocking {
        val registrationRequest = Registration(authProvider, dateOfBirth, email, name, password, phoneNumber)
        val registrationResponse = registerUseCase(registrationRequest).last()
        val result = GenericResponse(
            message = fakeAuthRepository.resourceMessage,
            success = true,
            payload = "Registration successful, OTP send to your email"
        )
        assertThat(registrationResponse).isEqualTo(Resource.Success(result))
    }
    @Test
    fun `Test to show registration was not successful`() = runBlocking {
        val errorMessage = "Registration not successful"
        fakeAuthRepository.shouldReturnError(true, errorMessage)
        val registrationRequest = Registration(authProvider, dateOfBirth, email, name, password, phoneNumber)
        val registrationResponse = registerUseCase(registrationRequest).last()
        assertThat(registrationResponse).isEqualTo(Resource.Error(400, errorMessage))
    }
}