package com.example.dispatchbuddy.domain.usecases.loginUseCases

import com.example.dispatchbuddy.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(val repository: AuthRepository) {
}