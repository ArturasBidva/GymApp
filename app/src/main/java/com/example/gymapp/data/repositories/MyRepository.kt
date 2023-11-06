package com.example.gymapp.data.repositories

import android.util.Log
import com.example.gymapp.data.api.ApiService
import com.example.gymapp.data.repositories.exercise.ExerciseWorkoutRequest
import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.domain.exercises.ExerciseCategory
import com.example.gymapp.domain.workouts.ExerciseWorkout
import com.example.gymapp.domain.workouts.Workout
import com.example.gymapp.util.Resource
import com.example.gymapp.util.UiText
import retrofit2.Response
import javax.inject.Inject

interface MyRepository {
    suspend fun getAllExercises(): Response<List<Exercise>>
    suspend fun updateExercise(id: Long, exercise: Exercise): Boolean
    suspend fun createExercise(exercise: Exercise): Resource<Unit>
    suspend fun getExerciseById(id: Long): Exercise
    suspend fun deleteExerciseById(id: Long): Resource<Unit>
    suspend fun deleteWorkoutById(id: Long): Resource<Unit>
    suspend fun getAllCategories(): Response<List<ExerciseCategory>>
    suspend fun getAllWorkouts(): Resource<List<Workout>>
    suspend fun getWorkoutById(id: Long): Workout
    suspend fun createWorkout(workout: Workout): Resource<Workout>
    suspend fun createExerciseWorkout(exerciseWorkout: ExerciseWorkout): Resource<ExerciseWorkout>
    suspend fun getAllExerciseWorkout(): List<ExerciseWorkout>
    suspend fun getExerciseWorkoutById(id: Long): ExerciseWorkout
    suspend fun updateExerciseWorkoutById(id: Long, exerciseWorkout: ExerciseWorkout): Boolean
    suspend fun addExerciseWorkoutToWorkout(workout: Workout, exerciseWorkout: ExerciseWorkout): Resource<Unit>
    suspend fun deleteExerciseWorkoutFromWorkoutById(workoutId: Long, exerciseId: Long): Resource<Unit>
}


class MyRepositoryImpl @Inject constructor(
    private val api: ApiService,
) : MyRepository {

    override suspend fun getAllExercises(): Response<List<Exercise>> {
        return api.getAllExercises()
    }

    override suspend fun updateExercise(id: Long, exercise: Exercise): Boolean {
        try {
            val response = api.updateExercise(id, exercise)
            if (response.isSuccessful) {
                Log.e("WorkoutRepository", "updateExercise Success")
                return true
            } else {
                Log.e("WorkoutRepository", "updateExercise failed")
            }
        } catch (e: Exception) {
            Log.e("WorkoutRepository", "updateExercise failed exception: ${e.message}")
        }
        return false
    }

    override suspend fun createExercise(exercise: Exercise): Resource<Unit> {
        return try {
            val response = api.createExercise(exercise)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error(message = UiText.DynamicString("Not authenticated"))
            }
        } catch (e: Exception) {
            Resource.Error(message = UiText.DynamicString(e.message.toString()))
        }
    }

    override suspend fun getExerciseById(id: Long): Exercise {
        return api.getExerciseById(id)
    }

    override suspend fun deleteExerciseById(id: Long): Resource<Unit> {
        return try {
            val response = api.deleteExerciseById(id)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error(message = UiText.DynamicString("Not authenticated"))
            }
        } catch (e: Exception) {
            Resource.Error(message = UiText.DynamicString(e.message.toString()))
        }
    }

    override suspend fun deleteWorkoutById(id: Long): Resource<Unit> {
        return try {
            val response = api.deleteWorkout(id)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error(message = UiText.DynamicString("Not authenticated"))
            }
        } catch (e: Exception) {
            Resource.Error(message = UiText.DynamicString(e.message.toString()))
        }
    }

    override suspend fun getAllCategories(): Response<List<ExerciseCategory>> {
        return api.getExerciseCategories()
    }

    override suspend fun getAllWorkouts(): Resource<List<Workout>> {
        return try {
            val response = api.getAllWorkouts()
            if (response.isSuccessful) {
                val workouts = response.body()
                if (workouts != null) {
                    Resource.Success(workouts)
                } else {
                    Resource.Error(message = UiText.DynamicString("Response body is null"))
                }
            } else {
                Resource.Error(message = UiText.DynamicString("Not authenticated"))
            }
        } catch (e: Exception) {
            Resource.Error(message = UiText.DynamicString(e.message.toString()))
        }
    }

    override suspend fun getWorkoutById(id: Long): Workout {
        return api.getWorkoutById(id)
    }

    override suspend fun createWorkout(workout: Workout): Resource<Workout> {
        try {
            val response = api.createWorkout(workout)
            if (response.isSuccessful) {
                Log.e("WorkoutRepository", "createWorkout Success")
                return Resource.Success(workout)
            } else {
                Log.e("WorkoutRepository", "createWorkout Failed")
            }
        } catch (e: Exception) {
            Log.e(
                "WorkoutRepository",
                "createWorkout failed with exception: ${e.message}"
            )
        }
        return Resource.Error(UiText.DynamicString("Failed to create workout"))
    }

    override suspend fun createExerciseWorkout(exerciseWorkout: ExerciseWorkout): Resource<ExerciseWorkout> {
        val response = api.createExerciseWorkout(exerciseWorkout)
        return if (response.isSuccessful) {
            Resource.Success(response.body())
        } else {
            Resource.Error(UiText.DynamicString("Something went wrong"))
        }
    }

    override suspend fun getAllExerciseWorkout(): List<ExerciseWorkout> {
        return api.getAllExerciseWorkout()
    }

    override suspend fun getExerciseWorkoutById(id: Long): ExerciseWorkout {
        return api.getExerciseWorkoutById(id)
    }

    override suspend fun updateExerciseWorkoutById(
        id: Long,
        exerciseWorkout: ExerciseWorkout
    ): Boolean {
        try {
            val response = api.updateExerciseWorkoutById(id, exerciseWorkout)
            if (response.isSuccessful) {
                Log.e("WorkoutRepository", "updateExerciseWorkoutById Success")
                return true
            } else {
                Log.e("WorkoutRepository", "updateExerciseWorkoutById Failed")
            }
        } catch (e: Exception) {
            Log.e(
                "WorkoutRepository",
                "updateExerciseWorkoutById Failed exception: ${e.message}"
            )
        }
        return false
    }

    override suspend fun addExerciseWorkoutToWorkout(
        workout: Workout,
        exerciseWorkout: ExerciseWorkout
    ): Resource<Unit> {
        try {
            val request =
                ExerciseWorkoutRequest(workoutId = workout.id!!, exerciseWorkout = exerciseWorkout)
            val response = api.addExerciseWorkoutToWorkout(request)
            if (response.isSuccessful) {
                Log.e("WorkoutRepository", "addExerciseWorkoutToWorkout Success")
                return Resource.Success(Unit)
            } else {
                Resource.Error(UiText.DynamicString("Error"), response.message())
                Log.e("WorkoutRepository", "addExerciseWorkoutToWorkout Failed")
            }
        } catch (e: Exception) {
            Log.e(
                "WorkoutRepository",
                "addExerciseWorkoutToWorkout failed exception: {${e.message}}"
            )
        }
        return Resource.Empty()
    }

    override suspend fun deleteExerciseWorkoutFromWorkoutById(
        workoutId: Long,
        exerciseId: Long
    ): Resource<Unit> {
        try {
            val response = api.deleteExerciseWorkoutFromWorkoutById(
                workoutId = workoutId,
                exerciseId = exerciseId
            )
            if (response.isSuccessful) {
                Log.e("WorkoutRepository", "deleteExerciseWorkoutFromWorkout Success")
                return Resource.Success(Unit)
            } else {
                Resource.Error(UiText.DynamicString("Error"), response.message())
                Log.e(
                    "WorkoutRepository",
                    "deleteExerciseWorkoutFromWorkout Failed" + response.message()
                )
            }
        } catch (e: Exception) {
            Log.e(
                "WorkoutRepository",
                "removeExerciseWorkoutFromWorkout failed exception: {${e.message}}"
            )
        }
        return Resource.Empty()
    }

}