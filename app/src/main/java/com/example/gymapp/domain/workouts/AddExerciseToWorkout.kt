package com.example.gymapp.domain.workouts

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class AddExerciseToWorkout(
    val exerciseWorkoutId: Long,
    val workoutId: Long
) : Parcelable {
}