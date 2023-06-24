package com.example.gymapp.domain.workouts

import android.os.Parcelable
import com.example.gymapp.domain.exercises.Exercise
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseWorkouts(
    val id: Long,
    val exercise: Exercise,
    val completedCount: Int = 0,
    val weight: Int,
    val goal: Int
) : Parcelable {
    constructor(
        exercise: Exercise,
        completedCount: Int,
        weight: Int,
        goal: Int
    ) : this(0L, exercise, completedCount, weight, goal)
}