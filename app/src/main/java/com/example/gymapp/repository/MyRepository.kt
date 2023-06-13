package com.example.gymapp.repository

import android.app.Application
import android.widget.Toast
import com.example.gymapp.R
import com.example.gymapp.api.ApiService
import com.example.gymapp.models.AddExerciseToWorkout
import com.example.gymapp.models.Exercise
import com.example.gymapp.models.ExerciseCategory
import com.example.gymapp.models.ExerciseWorkouts
import com.example.gymapp.models.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MyRepository {
    suspend fun getAllExercises(): List<Exercise>
    suspend fun updateExercise(id: Long, exercise: Exercise): Boolean
    suspend fun createExercise(exercise: Exercise): Boolean
    suspend fun getExerciseById(id: Long): Exercise
    suspend fun deleteExerciseById(id: Long): Boolean
    suspend fun getAllCategories(): List<ExerciseCategory>
    suspend fun getAllWorkouts(): List<Workout>
    suspend fun getWorkoutById(id: Long): Workout
    suspend fun createWorkout(workout: Workout): Boolean
    suspend fun createExerciseWorkout(exerciseWorkouts: ExerciseWorkouts): ExerciseWorkouts
    suspend fun getAllExerciseWorkout(): List<ExerciseWorkouts>
    suspend fun addExerciseToWorkout(addExerciseToWorkout: AddExerciseToWorkout): Boolean
    suspend fun getExerciseWorkoutById(id: Long): ExerciseWorkouts
}

class MyRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val appContext: Application
) : MyRepository {
    init {
        val appName = appContext.getString(R.string.app_name)
        println("Hello from $appName")
    }

    override suspend fun getAllExercises(): List<Exercise> {
        return api.getAllExercises()
    }

    override suspend fun updateExercise(id: Long, exercise: Exercise): Boolean {
        try {
            val response = api.updateExercise(id, exercise)
            if (response.isSuccessful) {
                Toast.makeText(
                    appContext,
                    "Exercise updated successfully",
                    Toast.LENGTH_SHORT
                ).show()
                return true
            } else {
                Toast.makeText(appContext, "Failed to update exercise", Toast.LENGTH_SHORT)
                    .show()
            }
        } catch (e: Exception) {
            Toast.makeText(appContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    override suspend fun createExercise(exercise: Exercise): Boolean {
        try {
            val response = api.createExercise(exercise)
            if (response.isSuccessful) {
                Toast.makeText(
                    appContext,
                    "Exercise saved successfully",
                    Toast.LENGTH_SHORT
                ).show()
                return true
            } else {
                Toast.makeText(appContext, "Failed to create exercise", Toast.LENGTH_SHORT)
                    .show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(appContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

    override suspend fun getExerciseById(id: Long): Exercise {
        return api.getExerciseById(id)
    }

    override suspend fun deleteExerciseById(id: Long): Boolean {
        try {
            val response = api.deleteExercise(id)
            if (response.isSuccessful) {
                Toast.makeText(
                    appContext,
                    "Exercise deleted successfully",
                    Toast.LENGTH_SHORT
                ).show()
                return true
            } else {
                Toast.makeText(appContext, "Failed to delete exercise", Toast.LENGTH_SHORT)
                    .show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(appContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

    override suspend fun getAllCategories(): List<ExerciseCategory> {
        return api.getExerciseCategories();
    }

    override suspend fun getAllWorkouts(): List<Workout> {
        return api.getAllWorkouts();
    }

    override suspend fun getWorkoutById(id: Long): Workout {
        return api.getWorkoutById(id)
    }

    override suspend fun createWorkout(workout: Workout): Boolean {
        try {
            val response = api.createWorkout(workout)
            if (response.isSuccessful) {
                Toast.makeText(
                    appContext,
                    "Workout saved successfully",
                    Toast.LENGTH_SHORT
                ).show()
                return true
            } else {
                Toast.makeText(appContext, "Failed to create workout", Toast.LENGTH_SHORT)
                    .show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(appContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

    override suspend fun createExerciseWorkout(exerciseWorkouts: ExerciseWorkouts): ExerciseWorkouts {
        return api.createExerciseWorkout(exerciseWorkouts)
    }

    override suspend fun getAllExerciseWorkout(): List<ExerciseWorkouts> {
        return api.getAllExerciseWorkout()
    }

    override suspend fun addExerciseToWorkout(addExerciseToWorkout: AddExerciseToWorkout): Boolean {
        try {
            val response = api.addExerciseToWorkout(addExerciseToWorkout)
            if (response.isSuccessful) {
                Toast.makeText(
                    appContext,
                    "Exercise added to workout successfully",
                    Toast.LENGTH_SHORT
                ).show()
                return true
            } else {
                Toast.makeText(appContext, "Failed to add exercise", Toast.LENGTH_SHORT)
                    .show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(appContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

    override suspend fun getExerciseWorkoutById(id: Long): ExerciseWorkouts {
        return api.getExerciseWorkoutById(id)
    }
}
