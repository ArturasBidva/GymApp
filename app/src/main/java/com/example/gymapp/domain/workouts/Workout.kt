package com.example.gymapp.domain.workouts

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Workout(
    val id: Long,
    val title: String,
    val description: String,
    val exerciseWorkouts : List<ExerciseWorkouts> = emptyList()
) : Parcelable