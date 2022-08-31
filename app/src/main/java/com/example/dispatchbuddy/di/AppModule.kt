package com.example.dispatchbuddy.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dispatchbuddy.common.Constants.BASE_URL
import com.example.dispatchbuddy.common.Constants.LOGIN_API
import com.example.dispatchbuddy.common.Constants.LOGIN_BASE_URL
import com.example.dispatchbuddy.common.Constants.MAIN_API
import com.example.dispatchbuddy.common.preferences.DispatchBuddyPreferences
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.data.local.DispatchBuddyDB
import com.example.dispatchbuddy.data.local.DispatchBuddyDao
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
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Named(MAIN_API)
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

    @Named(LOGIN_API)
    @Provides
    @Singleton
    fun provideLoginDispatchBuddyAPI(
        okHttpClient: OkHttpClient
    ): DispatchBuddyAPI {
        return Retrofit.Builder()
            .baseUrl(LOGIN_BASE_URL)
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

    // Room database
    @Provides
    @Singleton
    fun provideDispatchBuddyDatabase(
        application: Application
    ): DispatchBuddyDB{
        return Room.databaseBuilder(
            application,
            DispatchBuddyDB::class.java,
            "all_products_db",
        ).build()
    }

    @Provides
    @Singleton
    fun provideDispatchBuddyDao(database: DispatchBuddyDB): DispatchBuddyDao{
        return database.dao
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthInterceptorOkHttpClient

}