package com.example.gymapp.data.local

import com.example.gymapp.domain.workouts.ExerciseWorkout
import java.time.LocalDate
import java.time.LocalTime

data class WorkoutLocal(
    val id: Long,
    val title: String,
    val description: String,
    val schedules: List<Schedule>?,
    val exerciseWorkouts : List<ExerciseWorkout> = emptyList()
)

data class Schedule(
    val date: LocalDate?,
    val startTime: LocalTime?,
    val endTime: LocalTime?,
    var color: Int?
)