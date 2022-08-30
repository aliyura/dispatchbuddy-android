package com.example.dispatchbuddy.presentation.ui.rider_dashboard.di

import com.example.dispatchbuddy.common.Constants.MAIN_API
import com.example.dispatchbuddy.data.local.DispatchBuddyDB
import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import com.example.dispatchbuddy.data.repository.PagingSourceImpl
import com.example.dispatchbuddy.data.repository.RiderRepositoryImpl
import com.example.dispatchbuddy.domain.repository.PagingInterface
import com.example.dispatchbuddy.domain.repository.RiderRepository
import com.example.dispatchbuddy.domain.usecases.paginationUseCase.PaginationUseCase
import com.example.dispatchbuddy.domain.usecases.riderUseCases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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
    @Provides
    @Singleton
    fun provideUploadImageUseCase(
        riderRepository: RiderRepository
    ):UploadImageUseCase{
        return UploadImageUseCase(repository = riderRepository)
    }
    @Provides
    @Singleton
    fun provideGetUserUseCase(
        riderRepository: RiderRepository
    ):GetUserUseCase{
        return GetUserUseCase(repository = riderRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllUserRequestUseCase(
        riderRepository: RiderRepository
    ): GetAllUserRequestUseCases{
        return GetAllUserRequestUseCases(repository = riderRepository)
    }

    @Provides
    @Singleton
    fun provideRejectUserRequestUseCases(
        riderRepository: RiderRepository
    ): RejectUserRequestUseCase{
        return RejectUserRequestUseCase(repository = riderRepository)
    }

    @Provides
    @Singleton
    fun provideAcceptUserRequestUseCases(
        riderRepository: RiderRepository
    ): AcceptUserUseCase{
        return AcceptUserUseCase(repository = riderRepository)
    }

    @Provides
    @Singleton
    fun provideCloseUserRequestUseCases(
        riderRepository: RiderRepository
    ): CloseUserRequestUseCase{
        return CloseUserRequestUseCase(repository = riderRepository)
    }
    @Provides
    @Singleton
    fun provideGetPagingRequestUseCase(
        riderRepository: RiderRepository
    ): GetPagingRequestUseCase{
        return GetPagingRequestUseCase(repository = riderRepository)
    }
    @Provides
    @Singleton
    fun provideDeliveriesPagingUseCase(
        riderRepository: RiderRepository
    ): DeliveriesPagingUseCase{
        return DeliveriesPagingUseCase(repository = riderRepository)
    }
    // pagination with database
    @Provides
    @Singleton
    fun providePaginationUseCase(
        repository: PagingInterface
    ): PaginationUseCase{
        return PaginationUseCase(repository)
    }
    //provided Rider Repository
    @Provides
    @Singleton
    fun provideRiderRepository(
        @Named(MAIN_API) api: DispatchBuddyAPI
    ): RiderRepository{
        return RiderRepositoryImpl(api = api)
    }

    //provided pagination Repository
    @Provides
    @Singleton
    fun providePaginationRepository(
        @Named(MAIN_API) api: DispatchBuddyAPI,
        database: DispatchBuddyDB
    ): PagingInterface{
        return PagingSourceImpl(api = api, database)
    }
}