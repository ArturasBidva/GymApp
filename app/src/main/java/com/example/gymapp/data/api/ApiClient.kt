package com.example.gymapp.data.api

import android.app.Application
import com.example.gymapp.data.repositories.MyRepository
import com.example.gymapp.data.repositories.MyRepositoryImpl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiClient {
    private const val BASE_URL = "https://192.168.108.132:8080/"

    @Provides
    @Singleton
    fun provideMyApi(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // set connection timeout
            .readTimeout(30, TimeUnit.SECONDS) // set read timeout
            .writeTimeout(30, TimeUnit.SECONDS) // set write timeout
            .build()
    }

    @Provides
    @Singleton
    fun provideMyRepository(api: ApiService, app: Application): MyRepository {
        return MyRepositoryImpl(api, app)
    }
}
