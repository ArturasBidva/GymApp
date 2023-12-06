package com.example.gymapp.ui.screens.workoutschedule

import androidx.compose.ui.graphics.Color
import com.example.gymapp.data.local.Schedule
import com.example.gymapp.data.local.WorkoutLocal
import com.example.gymapp.domain.workouts.Workout
import java.time.LocalDate
import java.time.LocalTime

data class WorkoutScheduleUiState(
    var selectedDay: LocalDate? = null,
    val selectedWorkout: WorkoutLocal? = null,
    val isDialogVisible: Boolean = false,
    val isCalendarDialogVisible: Boolean = false,
    val startWorkoutTime: LocalTime? = null,
    val endWorkoutTime: LocalTime? = null,
    val timeSelectionDialogType: TimeSelectionDialogType? = null,
    val selectedColor: Color? = null,
    val daySchedule : Schedule? = null,
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