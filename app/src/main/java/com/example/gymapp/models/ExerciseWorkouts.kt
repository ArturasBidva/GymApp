package com.example.gymapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseWorkouts(
    val id: Long,
    val exercise: Exercise,
    val completedCount : Int = 0,
    val goal : Int
    ) : Parcelable