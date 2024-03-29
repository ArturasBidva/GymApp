package com.example.gymapp.data.repositories.local.exercise

import com.example.gymapp.domain.workouts.ExerciseWorkout

data class ExerciseWorkoutRequest(
    val workoutId: Long,
    val exerciseWorkout: ExerciseWorkout
)