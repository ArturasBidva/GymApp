package com.example.gymapp.domain.workouts

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddExerciseToWorkout(
    val exerciseWorkoutId: Long,
    val workoutId: Long
) : Parcelable