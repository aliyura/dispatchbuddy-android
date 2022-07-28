package com.example.dispatchbuddy.di

import android.content.Context
import com.example.dispatchbuddy.common.Constants.BASE_URL
import com.example.dispatchbuddy.common.network.AppDispatchers
import com.example.dispatchbuddy.common.network.IAppDispatchers
import com.example.dispatchbuddy.common.preferences.DispatchBuddyPreferences
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDispatchBuddyAPI(
        @AuthInterceptorOkHttpClient okHttpClient: OkHttpClient
    ): DispatchBuddyAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(DispatchBuddyAPI::class.java)
    }

    @AuthInterceptorOkHttpClient
    @Provides
    @Singleton
    fun providesOkhttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor).build()

    @Provides
    @Singleton
    fun provideLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun providePreference(
        @ApplicationContext context: Context
    ): Preferences {
        return DispatchBuddyPreferences(context = context)
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthInterceptorOkHttpClient

//    @Provides
//    @Singleton
//    fun provideAppDispatchers():
//            IAppDispatchers = AppDispatchers()
}