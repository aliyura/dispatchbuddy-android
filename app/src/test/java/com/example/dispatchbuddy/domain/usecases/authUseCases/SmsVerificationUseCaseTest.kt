package com.example.dispatchbuddy.domain.usecases.authUseCases

import com.example.dispatchbuddy.Constants.userProfileTest
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.UserProfile
import com.example.dispatchbuddy.data.remote.dto.models.UserProfileTest
import com.example.dispatchbuddy.data.remote.dto.models.VerifyUser
import com.example.dispatchbuddy.domain.repository.TestAuthRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SmsVerificationUseCaseTest {
    private lateinit var smsVerificationUseCase: SmsVerificationUseCase
    private lateinit var fakeAuthRepository: TestAuthRepository
    val otp: String = "123456"
    val username: String = "iniyealakeret@gmail.com"

    @Before
    fun setUp() {
        fakeAuthRepository = TestAuthRepository()
        smsVerificationUseCase = SmsVerificationUseCase(fakeAuthRepository)
    }
    @Test
    fun `Flow emitting verify user Loading Result State`() = runBlocking {
        val smVerificationRequest = smsVerificationUseCase.invoke(VerifyUser(otp = otp, username = username)).first()
        Truth.assertThat(smVerificationRequest).isEqualTo(Resource.Loading<Any>("Loading..."))
    }
    @Test
    fun `Test to show success when user is verified with correct parameters`() = runBlocking{
        val smVerificationRequest = smsVerificationUseCase.invoke(VerifyUser(otp = otp, username = username)).last()
        val verifyUserResponse = GenericResponse(
            message = fakeAuthRepository.resourceMessage,
            success = true,
            payload = UserProfile(
                accountType = userProfileTest.accountType,
                authProvider = userProfileTest.authProvider,
                city = userProfileTest.city,
                country = userProfileTest.country,
                createdDate = userProfileTest.createdDate,
                dp = userProfileTest.dp,
                dateOfBirth = userProfileTest.dateOfBirth,
                email = username,
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
        Truth.assertThat(smVerificationRequest).isEqualTo(Resource.Success(verifyUserResponse))
    }
}