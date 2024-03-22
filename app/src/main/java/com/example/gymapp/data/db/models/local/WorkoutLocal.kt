package com.example.gymapp.data.db.models.local

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gymapp.domain.workouts.ExerciseWorkout
import com.example.gymapp.domain.workouts.Workout
import java.time.LocalDate
import java.time.LocalTime

data class WorkoutLocal(
    val id: Long = 0L,
    val title: String = "",
    val description: String = "",
    val exerciseWorkouts: List<ExerciseWorkout> = listOf()
)

data class Schedule(
    val id: Long = 0,
    var workout: WorkoutLocal = WorkoutLocal(),
    var date: LocalDate = LocalDate.now(),
    var startTime: LocalTime? = null,
    var endTime: LocalTime? = null,
    var color: Int = Color.Blue.toArgb()
) {

    fun getSchedulesByDay() {
        val schedules: List<Schedule> = listOf()
        schedules.filter { it.date == LocalDate.now() }
    }

}