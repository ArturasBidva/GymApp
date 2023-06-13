package com.example.gymapp.api

import com.example.gymapp.models.AddExerciseToWorkout
import com.example.gymapp.models.Exercise
import com.example.gymapp.models.ExerciseCategory
import com.example.gymapp.models.ExerciseWorkouts
import com.example.gymapp.models.User
import com.example.gymapp.models.Workout
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

    @GET("categories")
    suspend fun getExerciseCategories(): List<ExerciseCategory>

    @POST("register")
    suspend fun postUser(@Body User: User): Response<Unit>

    @GET("/workouts")
    suspend fun getAllWorkouts(): List<Workout>

    @GET("/get/workout/{id}")
    suspend fun getWorkoutById(@Path("id") id: Long): Workout

    @POST("/workout")
    suspend fun createWorkout(@Body workout: Workout): Response<Void>

    @POST("/exerciseworkout")
    suspend fun createExerciseWorkout(@Body exerciseWorkouts: ExerciseWorkouts): ExerciseWorkouts

    @GET("/get/exerciseworkout")
    suspend fun getAllExerciseWorkout(): List<ExerciseWorkouts>

    @POST("/add/workout")
    suspend fun addExerciseToWorkout(@Body addExerciseToWorkout: AddExerciseToWorkout): Response<Void>

    @GET("/get/exerciseworkout/{id}")
    suspend fun getExerciseWorkoutById(@Path("id") id: Long): ExerciseWorkouts
}