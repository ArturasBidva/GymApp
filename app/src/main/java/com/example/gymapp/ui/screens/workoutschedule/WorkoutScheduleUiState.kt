package com.example.gymapp.ui.screens.workoutschedule

import com.example.gymapp.domain.workouts.Workout
import java.time.LocalDate
import java.time.LocalTime

data class WorkoutScheduleUiState(
    val selectedDay: LocalDate? = null,
    val selectedWorkout: Workout? = null,
    val isDialogVisible: Boolean = false,
    val isCalendarDialogVisible: Boolean = false,
    val startWorkoutTime: LocalTime? = null,
    val endWorkoutTime: LocalTime? = null,
    val timeSelectionDialogType: TimeSelectionDialogType? = null,
) {
    fun getTimeSelectionTime(): LocalTime? {
        return when (timeSelectionDialogType) {
            is TimeSelectionDialogType.StartTime -> {
                startWorkoutTime
            }

            is TimeSelectionDialogType.EndTime -> {
                endWorkoutTime
            }

            else -> null
        }
    }
}