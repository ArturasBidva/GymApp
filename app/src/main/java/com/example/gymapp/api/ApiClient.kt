package com.example.gymapp.api

import com.example.gymapp.repository.MyRepository
import com.example.gymapp.repository.MyRepositoryImpl
import android.app.Application
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiClient {
    private const val BASE_URL = "http://192.168.45.204:8080/"

    @Provides
    @Singleton
    fun provideMyApi(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMyRepository(api : ApiService, app : Application): MyRepository {
        return MyRepositoryImpl(api,app)
    }
}
