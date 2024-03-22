package com.example.gymapp.data.api

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
    private const val BASE_URL = "http://192.168.0.103:8080/"

    @Provides
    @Singleton
    fun provideMyApi(okHttpClient: OkHttpClient): ApiService {

        try{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(ApiService::class.java)
        }
        catch(e: Exception){
            // caught and handles it
        }


        return TODO("Provide the return value")
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {


        try{




            return OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
        }
        catch(e: Exception){
            // caught and handles it
        }


        return TODO("Provide the return value")



    }

    @Provides
    @Singleton
    fun provideMyRepository(api: ApiService): MyRepository {
        return MyRepositoryImpl(api)
    }
}
