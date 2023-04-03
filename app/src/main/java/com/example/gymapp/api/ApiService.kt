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
    fun getExerciseById(@Path("id") id: Long): Call<Exercise>

    @POST("exercise")
    fun createExercise(@Body exercise: Exercise): Call<Exercise>

    @POST("exercise/update/{id}")
    suspend fun updateExercise(@Path("id") id: Long, @Body exercise: Exercise)

    @DELETE("exercise/{id}")
    fun deleteExercise(@Path("id") id: Long): Call<Void>

    @POST("register")
    suspend fun postUser(@Body User: User): Response<Unit>

}