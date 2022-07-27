package com.example.dispatchbuddy.presentation.ui.user_dashboard.di

import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import com.example.dispatchbuddy.data.repository.UserRepositoryImpl
import com.example.dispatchbuddy.domain.repository.UserRepository
import com.example.dispatchbuddy.domain.usecases.userUseCases.HomeUseCase
import com.example.dispatchbuddy.domain.usecases.userUseCases.RiderListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {
    // provide user usecases here
    @Provides
    @Singleton
    fun provideHomeUseCase(
        userRepository: UserRepository
    ): HomeUseCase{
        return HomeUseCase(repository = userRepository)
    }
    @Provides
    @Singleton
    fun provideRiderListUseCase(
        userRepository: UserRepository
    ): RiderListUseCase{
        return RiderListUseCase(repository = userRepository)
    }
    // provided the user Repository
    @Provides
    @Singleton
    fun provideUserRepository(
        api: DispatchBuddyAPI
    ): UserRepository{
        return UserRepositoryImpl(api = api)
    }
}