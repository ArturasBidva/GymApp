package com.example.gymapp.api

import com.example.gymapp.models.Exercise
import com.example.gymapp.models.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    @GET("exercises")
    suspend fun getAllExercises(): List<Exercise>

    @GET("exercise/get/{id}")
   suspend fun getExerciseById(@Path("id") id: Long): Exercise

    @POST("exercise")
    suspend fun createExercise(@Body exercise: Exercise): Response<Void>

    @POST("exercise/update/{id}")
    suspend fun updateExercise(@Path("id") id: Long, @Body exercise: Exercise): Response<Void>

    @DELETE("exercise/{id}")
    suspend fun deleteExercise(@Path("id") id: Long): Response<Void>

    @POST("register")
    suspend fun postUser(@Body User: User): Response<Unit>

}