package com.example.gymapp.data.repositories.local.workout

import com.example.gymapp.data.db.entities.ExerciseWorkoutEntity
import com.example.gymapp.data.db.entities.WorkoutWithExerciseWorkoutPair
import com.example.gymapp.data.db.models.local.WorkoutLocal
import com.example.gymapp.data.repositories.local.exercise.DataExerciseService
import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.domain.workouts.ExerciseWorkout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataWorkoutService @Inject constructor(
    private val dataWorkoutRepository: DataWorkoutRepository,
    private val dataExerciseService: DataExerciseService
) {
    suspend fun getAllWorkouts(): Flow<List<WorkoutLocal>> {
       return dataWorkoutRepository.getWorkouts().map { it -> it.map { it.toWorkoutLocal() } }
    }



    private fun WorkoutWithExerciseWorkoutPair.toWorkoutLocal(): WorkoutLocal{
       return WorkoutLocal(
            id = this.workoutEntity.id,
            title = this.workoutEntity.title,
            description = this.workoutEntity.description,
            exerciseWorkouts = this.exerciseWorkouts.map { it.toExerciseWorkout() }
        )
    }

    private fun ExerciseWorkoutEntity.toExerciseWorkout(): ExerciseWorkout {
        return ExerciseWorkout(
            id = this.exerciseWorkoutId,
            completedCount = this.completedCount,
            weight = this.weight,
            goal = this.goal,
            exercise = dataExerciseService.getExerciseById(this.exerciseId)
        )
    }
}



