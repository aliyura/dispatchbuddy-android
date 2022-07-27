package com.example.dispatchbuddy.domain.usecases.authUseCases

import com.example.dispatchbuddy.domain.repository.AuthRepository
import javax.inject.Inject

class SmsVerificationUseCase @Inject constructor(val repository: AuthRepository){

}