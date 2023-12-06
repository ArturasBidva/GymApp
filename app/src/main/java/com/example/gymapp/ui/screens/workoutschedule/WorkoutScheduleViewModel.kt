package com.example.gymapp.ui.screens.workoutschedule

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.data.local.Schedule
import com.example.gymapp.data.local.WorkoutLocal
import com.example.gymapp.domain.workouts.WorkoutService
import com.example.gymapp.util.TimeValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class WorkoutScheduleViewModel @Inject constructor(
    private val workoutService: WorkoutService
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkoutScheduleUiState())
    val uiState: StateFlow<WorkoutScheduleUiState> = _uiState


    fun selectWorkoutDate(day: LocalDate?) {
        _uiState.update { it.copy(selectedDay = day) }
    }

    fun addWorkoutToSchedule(workout: WorkoutLocal) {
        viewModelScope.launch {
            workoutService.addWorkoutToSchedule(workoutLocal = workout)
        }
    }

    fun setWorkoutScheduleDialogVisibility(visible: Boolean) {
        _uiState.update {
            it.copy(isDialogVisible = visible)
        }
    }

    fun setWorkoutScheduleDateDialogVisibility(visible: Boolean) {
        _uiState.update { it.copy(isCalendarDialogVisible = visible) }
    }

    fun selectColor(color: Color) {
        _uiState.update { it.copy(selectedColor = color) }
    }

    fun deleteWorkoutSchedule(workout: WorkoutLocal,selectedDay : LocalDate) {
        viewModelScope.launch {
            workoutService.removeWorkoutFromSchedule(workoutLocal = workout, dayOfWorkout = selectedDay)
        }
    }

    fun selectWorkout(workout: WorkoutLocal?) {
        _uiState.update { it.copy(selectedWorkout = workout) }
    }

    fun validateSelectedTime(time: Long): Boolean {
        val previousDayMillis = Date().time - TimeUnit.DAYS.toMillis(1L)
        return Date(time).after(Date(previousDayMillis))
    }

    fun onTimeConfirmation(time: LocalTime) {
        uiState.value.timeSelectionDialogType?.let { type ->
            when (type) {
                is TimeSelectionDialogType.StartTime -> {
                    if (TimeValidator.validateStartTime(
                            startTime = time,
                            endTime = uiState.value.endWorkoutTime
                        )
                    ) {
                        _uiState.update {
                            it.copy(
                                startWorkoutTime = time,
                                timeSelectionDialogType = null
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                startWorkoutTime = null,
                                timeSelectionDialogType = null
                            )
                        }
                    }
                }

                is TimeSelectionDialogType.EndTime -> {
                    if (TimeValidator.validateEndTime(
                            startTime = uiState.value.startWorkoutTime,
                            endTime = time
                        )
                    ) {
                        _uiState.update {
                            it.copy(
                                endWorkoutTime = time,
                                timeSelectionDialogType = null
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                endWorkoutTime = null,
                                timeSelectionDialogType = null
                            )
                        }
                    }
                }
            }
        }
    }

    fun onTimePickerDismiss() {
        _uiState.update { it.copy(timeSelectionDialogType = null) }
    }


    fun onOpenTimePickerClick(dialogType: TimeSelectionDialogType) {
        _uiState.update { it.copy(timeSelectionDialogType = dialogType) }
    }
}