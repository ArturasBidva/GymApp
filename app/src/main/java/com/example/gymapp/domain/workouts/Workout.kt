package com.example.gymapp.domain.workouts

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
data class Workout(
    val id: Long?,
    val title: String,
    val description: String,
    val date: LocalDate? = null,
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null,
    val exerciseWorkouts : List<ExerciseWorkout> = emptyList()
) : Parcelable