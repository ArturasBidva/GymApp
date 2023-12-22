package com.example.gymapp.data.api

import com.example.gymapp.data.repositories.local.exercise.ExerciseWorkoutRequest
import com.example.gymapp.domain.account.User
import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.domain.exercises.ExerciseCategory
import com.example.gymapp.domain.workouts.ExerciseWorkout
import com.example.gymapp.domain.workouts.Workout
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    @GET("exercises")
    suspend fun getAllExercises(): Response<List<Exercise>>

    @GET("exercise/get/{id}")
    suspend fun getExerciseById(@Path("id") id: Long): Exercise

    @POST("exercise")
    suspend fun createExercise(@Body exercise: Exercise): Response<Unit>

    @POST("exercise/update/{id}")
    suspend fun updateExercise(@Path("id") id: Long, @Body exercise: Exercise): Response<Void>

    @DELETE("exercise/{id}")
    suspend fun deleteExerciseById(@Path("id") id: Long): Response<Unit>

    @DELETE("delete/workout/{id}")
    suspend fun deleteWorkout(@Path("id") id: Long): Response<Unit>

    @GET("categories")
    suspend fun getExerciseCategories(): Response<List<ExerciseCategory>>

    @POST("register")
    suspend fun postUser(@Body User: User): Response<Unit>

    @GET("/workouts")
    suspend fun getAllWorkouts(): Response<List<Workout>>

    @GET("/get/workout/{id}")
    suspend fun getWorkoutById(@Path("id") id: Long): Workout

    @POST("/workout")
    suspend fun createWorkout(@Body workout: Workout): Response<Workout>

    @POST("/exerciseworkout/{id}")
    suspend fun updateExerciseWorkoutById(
        @Path("id") id: Long,
        @Body exerciseWorkout: ExerciseWorkout
    ): Response<Void>

    @POST("/exerciseworkout")
    suspend fun createExerciseWorkout(@Body exerciseWorkout: ExerciseWorkout): Response<ExerciseWorkout>

    @GET("/get/exerciseworkout")
    suspend fun getAllExerciseWorkout(): List<ExerciseWorkout>

    @POST("/add/workout/exerciseworkout")
    suspend fun addExerciseWorkoutToWorkout(@Body exerciseWorkoutRequest: ExerciseWorkoutRequest): Response<Void>

    @GET("/get/exerciseworkout/{id}")
    suspend fun getExerciseWorkoutById(@Path("id") id: Long): ExerciseWorkout

    @DELETE("/delete/exerciseworkout/{workoutId}/{exerciseId}")
    suspend fun deleteExerciseWorkoutFromWorkoutById(
        @Path("workoutId") workoutId: Long,
        @Path("exerciseId") exerciseId: Long
    ): Response<Unit>

}