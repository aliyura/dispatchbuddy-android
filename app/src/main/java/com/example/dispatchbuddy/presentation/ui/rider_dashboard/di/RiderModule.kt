package com.example.dispatchbuddy.presentation.ui.rider_dashboard.di

import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import com.example.dispatchbuddy.data.repository.RiderRepositoryImpl
import com.example.dispatchbuddy.domain.repository.RiderRepository
import com.example.dispatchbuddy.domain.usecases.riderUseCases.DeliveriesUseCase
import com.example.dispatchbuddy.domain.usecases.riderUseCases.LocationUseCase
import com.example.dispatchbuddy.domain.usecases.riderUseCases.ProfileUseCase
import com.example.dispatchbuddy.domain.usecases.riderUseCases.RequestUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RiderModule {
    //provide Rider usecases here
    @Provides
    @Singleton
    fun provideProfileUseCase(
       riderRepository: RiderRepository
    ):ProfileUseCase{
        return ProfileUseCase(repository = riderRepository)
    }
    @Provides
    @Singleton
    fun provideLocationUseCase(
        riderRepository: RiderRepository
    ):LocationUseCase{
        return LocationUseCase(repository = riderRepository)
    }
    @Provides
    @Singleton
    fun provideDeliveriesUseCase(
        riderRepository: RiderRepository
    ):DeliveriesUseCase{
        return DeliveriesUseCase(repository = riderRepository)
    }
    @Provides
    @Singleton
    fun provideRequestUseCase(
        riderRepository: RiderRepository
    ):RequestUseCase{
        return RequestUseCase(repository = riderRepository)
    }
    //provided Rider Repository
    @Provides
    @Singleton
    fun provideRiderRepository(
        api: DispatchBuddyAPI
    ): RiderRepository{
        return RiderRepositoryImpl(api = api)
    }
}