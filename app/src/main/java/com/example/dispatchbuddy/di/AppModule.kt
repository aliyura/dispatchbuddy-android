package com.example.dispatchbuddy.di

import android.content.Context
import android.content.SharedPreferences
import com.example.dispatchbuddy.common.Constants.BASE_URL
import com.example.dispatchbuddy.common.preferences.DispatchBuddyPreferences
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.data.remote.network.DispatchBuddyAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
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
        okHttpClient: OkHttpClient
    ): DispatchBuddyAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(DispatchBuddyAPI::class.java)
    }

    /*Add authorization to the header interceptor*/
    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                request.addHeader("Username", "web-client")
                request.addHeader("Password", "password")
            chain.proceed(request.build())
        }
    }


    @Provides
    @Singleton
    fun providesOkhttp(headerAuthorization: Interceptor, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .addInterceptor(headerAuthorization)
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

}