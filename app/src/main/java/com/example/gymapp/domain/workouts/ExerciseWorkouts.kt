package com.example.gymapp.domain.workouts

import android.os.Parcelable
import com.example.gymapp.domain.exercises.Exercise
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseWorkouts(
    val id: Long = 0,
    val completedCount: Int = 0,
    val weight: Int,
    val goal: Int,
    val exercise: Exercise
) : Parcelable