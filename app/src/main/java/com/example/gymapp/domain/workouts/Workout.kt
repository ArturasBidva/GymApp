package com.example.gymapp.domain.workouts

import android.os.Parcelable
import androidx.annotation.ColorRes
import com.example.gymapp.R
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
data class Workout(
    val id: Long?,
    val title: String,
    val description: String,
    val exerciseWorkouts : List<ExerciseWorkout> = emptyList(),
) : Parcelable