package com.example.dispatchbuddy.presentation.ui.authentication.di

import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import com.example.dispatchbuddy.data.repository.AuthRepositoryImpl
import com.example.dispatchbuddy.domain.repository.AuthRepository
import com.example.dispatchbuddy.domain.usecases.loginUseCases.LoginUseCase
import com.example.dispatchbuddy.domain.usecases.loginUseCases.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    // provide Authentication usecases here
    @Provides
    @Singleton
    fun provideLoginUseCase(
        authRepository: AuthRepository
    ): LoginUseCase {
        return LoginUseCase(repository = authRepository)
    }
    @Provides
    @Singleton
    fun provideRegisterUseCase(
        authRepository: AuthRepository
    ): RegisterUseCase {
        return RegisterUseCase(repository = authRepository)
    }
    //provide repository
    @Provides
    @Singleton
    fun provideAuthRepository(
        api: DispatchBuddyAPI
    ): AuthRepository{
        return AuthRepositoryImpl(api = api)
    }
}