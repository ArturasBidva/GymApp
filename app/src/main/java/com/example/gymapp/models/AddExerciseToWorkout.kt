package com.example.gymapp.models

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class AddExerciseToWorkout(
    val exerciseWorkoutId: Long,
    val workoutId: Long
) : Parcelable {
}