package com.example.gymapp.ui.screens.workoutschedule

import com.example.gymapp.data.db.models.local.Schedule
import com.example.gymapp.data.db.models.local.WorkoutLocal
import java.time.LocalDate
import java.time.LocalTime

data class WorkoutScheduleUiState(
    val selectedWorkout: WorkoutLocal = WorkoutLocal(),
    val schedule: Schedule = Schedule(),
    val schedules: List<Schedule> = listOf(),
    val isDialogVisible: Boolean = false,
    val isCalendarDialogVisible: Boolean = false,
    val timeSelectionDialogType: TimeSelectionDialogType? = null,
    val selectedCalendarDate: LocalDate? = null,
    val isEditMode: Boolean = false,
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
    val isLoading: Boolean = false
=======
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes
) {
    fun getTimeSelectionTime(): LocalTime? {
        return when (timeSelectionDialogType) {
            is TimeSelectionDialogType.StartTime -> schedule.startTime
            is TimeSelectionDialogType.EndTime -> schedule.endTime

            else -> null
        }
    }
}