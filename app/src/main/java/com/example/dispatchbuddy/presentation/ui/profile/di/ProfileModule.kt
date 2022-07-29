package com.example.dispatchbuddy.presentation.ui.profile.di

import com.example.dispatchbuddy.common.Constants.MAIN_API
import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import com.example.dispatchbuddy.data.repository.ProfileRepositoryImpl
import com.example.dispatchbuddy.domain.repository.ProfileRepository
import com.example.dispatchbuddy.domain.usecases.profileUseCases.EditProfileUsesCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    //provide Profile usecases here
    @Provides
    @Singleton
    fun provideEditProfileUseCase(
        profileRepository: ProfileRepository
    ): EditProfileUsesCase{
        return EditProfileUsesCase(repository = profileRepository)
    }
    // Provided Profile ProfileRepository
    @Provides
    @Singleton
    fun provideProfileRepository(
        @Named(MAIN_API) api: DispatchBuddyAPI
    ): ProfileRepository{
        return ProfileRepositoryImpl(api = api)
    }
}