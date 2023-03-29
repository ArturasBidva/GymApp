package com.example.gymapp.api

import com.example.gymapp.repository.MyRepository
import com.example.gymapp.repository.MyRepositoryImpl
import android.app.Application
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
    private const val BASE_URL = "http://45.9.191.191:8080/"

    @Provides
    @Singleton
    fun provideMyApi(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMyRepository(api : ApiService, app : Application): MyRepository {
        return MyRepositoryImpl(api,app)
    }
}
