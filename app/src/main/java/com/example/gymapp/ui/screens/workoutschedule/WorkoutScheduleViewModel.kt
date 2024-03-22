package com.example.gymapp.ui.screens.workoutschedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.data.db.models.local.Schedule
import com.example.gymapp.data.db.models.local.WorkoutLocal
import com.example.gymapp.data.repositories.local.schedule.ScheduleService
import com.example.gymapp.util.TimeValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class WorkoutScheduleViewModel @Inject constructor(
    private val scheduleService: ScheduleService
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkoutScheduleUiState())
    val uiState: StateFlow<WorkoutScheduleUiState> = _uiState

    init {
        getAllSchedules()
    }

    private fun getAllSchedules() {
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
        viewModelScope.launch {
            scheduleService.getAllSchedules().onStart {
                _uiState.update { it.copy(isLoading = true) }
            }.onEach { WorkoutScheduleUiState(isLoading = true) }.catch {
                _uiState.update { it.copy(isLoading = false) }
            }.collect { items -> _uiState.update { it.copy(schedules = items, isLoading = false) } }

        }
    }

        fun createSchedule() {
            viewModelScope.launch {
                val workoutScheduleUiState = uiState.value
                val schedule = Schedule(
                    workout = workoutScheduleUiState.selectedWorkout,
                    startTime = workoutScheduleUiState.schedule.startTime,
                    endTime = workoutScheduleUiState.schedule.endTime,
                    date = workoutScheduleUiState.selectedCalendarDate!!,
                    color = workoutScheduleUiState.schedule.color
                )
                scheduleService.insertSchedule(schedule = schedule)
            }
=======
>>>>>>> Stashed changes
       viewModelScope.launch {
           val schedules = scheduleService.getAllSchedules()
           _uiState.update { it.copy(schedules = schedules) }
       }
    }

    fun createSchedule() {
        viewModelScope.launch {
            val workoutScheduleUiState = uiState.value
            val schedule = Schedule(
                workout = workoutScheduleUiState.selectedWorkout,
                startTime = workoutScheduleUiState.schedule.startTime,
                endTime = workoutScheduleUiState.schedule.endTime,
                date = workoutScheduleUiState.selectedCalendarDate!!,
                color = workoutScheduleUiState.schedule.color
            )
            scheduleService.insertSchedule(schedule = schedule)
        }
    }


    fun setWorkoutScheduleDialogVisibility(visible: Boolean) {
        _uiState.update {
            val scheduleWithDate =
                uiState.value.schedule.copy(date = uiState.value.selectedCalendarDate!!)
            it.copy(isDialogVisible = visible, isEditMode = false, schedule = scheduleWithDate)
<<<<<<< Updated upstream
=======
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes
        }


<<<<<<< Updated upstream
=======
<<<<<<< HEAD
        fun setWorkoutScheduleDialogVisibility(visible: Boolean) {
            _uiState.update {
                val scheduleWithDate =
                    uiState.value.schedule.copy(date = uiState.value.selectedCalendarDate!!)
                it.copy(isDialogVisible = visible, isEditMode = false, schedule = scheduleWithDate)
            }
        }


        fun setWorkoutScheduleDateDialogVisibility(visible: Boolean) {
            _uiState.update {
                it.copy(isCalendarDialogVisible = visible)
            }
=======
>>>>>>> Stashed changes

    fun setWorkoutScheduleDateDialogVisibility(visible: Boolean) {
        _uiState.update {
            it.copy(isCalendarDialogVisible = visible)
<<<<<<< Updated upstream
        }
    }

    fun deleteSchedule(scheduleId: Long) {
        viewModelScope.launch {
            scheduleService.deleteScheduleById(scheduleId = scheduleId)
        }
    }

    fun updateSchedule(schedule: Schedule) {
        _uiState.update { it.copy(schedule = schedule) }
    }

    fun onWorkoutSelect(workout: WorkoutLocal) {
        _uiState.update { it.copy(selectedWorkout = workout) }
    }

    fun validateSelectedTime(time: Long): Boolean {
        val previousDayMillis = Date().time - TimeUnit.DAYS.toMillis(1L)
        return Date(time).after(Date(previousDayMillis))
    }


    fun onCalendarDateSelection(date: LocalDate?) {
        _uiState.update { it.copy(selectedCalendarDate = date) }
    }

    fun onEditScheduleSelect(schedule: Schedule) {
        _uiState.update {
            it.copy(
                schedule = schedule,
                selectedWorkout = schedule.workout,
                isDialogVisible = true,
                isEditMode = true
            )
        }
    }

=======
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
        }

<<<<<<< HEAD
        fun deleteSchedule(scheduleId: Long) {
            viewModelScope.launch {
                scheduleService.deleteScheduleById(scheduleId = scheduleId)
            }
        }
=======
    fun deleteSchedule(scheduleId: Long) {
        viewModelScope.launch {
            scheduleService.deleteScheduleById(scheduleId = scheduleId)
        }
    }

    fun updateSchedule(schedule: Schedule) {
        _uiState.update { it.copy(schedule = schedule) }
    }

    fun onWorkoutSelect(workout: WorkoutLocal) {
        _uiState.update { it.copy(selectedWorkout = workout) }
    }
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f

        fun updateSchedule(schedule: Schedule) {
            _uiState.update { it.copy(schedule = schedule) }
        }

<<<<<<< HEAD
        fun onWorkoutSelect(workout: WorkoutLocal) {
            _uiState.update { it.copy(selectedWorkout = workout) }
        }

        fun validateSelectedTime(time: Long): Boolean {
            val previousDayMillis = Date().time - TimeUnit.DAYS.toMillis(1L)
            return Date(time).after(Date(previousDayMillis))
        }


        fun onCalendarDateSelection(date: LocalDate?) {
            _uiState.update { it.copy(selectedCalendarDate = date) }
        }

        fun onEditScheduleSelect(schedule: Schedule) {
            _uiState.update {
                it.copy(
                    schedule = schedule,
                    selectedWorkout = schedule.workout,
                    isDialogVisible = true,
                    isEditMode = true
                )
            }
        }

        fun onTimeConfirmation(time: LocalTime) {
            val schedule = uiState.value.schedule

            uiState.value.timeSelectionDialogType?.let { type ->
                when (type) {
                    is TimeSelectionDialogType.StartTime -> {
                        if (TimeValidator.validateStartTime(
                                startTime = time,
                                endTime = uiState.value.schedule.endTime
=======

    fun onCalendarDateSelection(date: LocalDate?) {
        _uiState.update { it.copy(selectedCalendarDate = date) }
    }

    fun onEditScheduleSelect(schedule: Schedule) {
        _uiState.update {
            it.copy(
                schedule = schedule,
                selectedWorkout = schedule.workout,
                isDialogVisible = true,
                isEditMode = true
            )
        }
    }

>>>>>>> Stashed changes
    fun onTimeConfirmation(time: LocalTime) {
        val schedule = uiState.value.schedule

        uiState.value.timeSelectionDialogType?.let { type ->
            when (type) {
                is TimeSelectionDialogType.StartTime -> {
                    if (TimeValidator.validateStartTime(
                            startTime = time,
                            endTime = uiState.value.schedule.endTime
                        )
                    ) {
                        _uiState.update {
                            val scheduleWithStartTime = schedule.copy(startTime = time)
                            it.copy(
                                schedule = scheduleWithStartTime,
                                timeSelectionDialogType = null
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                schedule = schedule.copy(startTime = null),
                                timeSelectionDialogType = null
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
                            )
                        ) {
                            _uiState.update {
                                val scheduleWithStartTime = schedule.copy(startTime = time)
                                it.copy(
                                    schedule = scheduleWithStartTime,
                                    timeSelectionDialogType = null
                                )
                            }
                        } else {
                            _uiState.update {
                                it.copy(
                                    schedule = schedule.copy(startTime = null),
                                    timeSelectionDialogType = null
                                )
                            }
                        }
                    }

<<<<<<< HEAD
                    is TimeSelectionDialogType.EndTime -> {
                        val scheduleWithEndTime = schedule.copy(endTime = time)
                        if (TimeValidator.validateEndTime(
                                startTime = uiState.value.schedule.startTime,
                                endTime = time
                            )
                        ) {
                            _uiState.update {

                                it.copy(
                                    schedule = scheduleWithEndTime,
                                    timeSelectionDialogType = null
                                )
                            }
                        } else {
                            _uiState.update {
                                it.copy(
                                    schedule = schedule.copy(endTime = null),
                                    timeSelectionDialogType = null
                                )
                            }
=======
                is TimeSelectionDialogType.EndTime -> {
                    val scheduleWithEndTime = schedule.copy(endTime = time)
                    if (TimeValidator.validateEndTime(
                            startTime = uiState.value.schedule.startTime,
                            endTime = time
                        )
                    ) {
                        _uiState.update {

                            it.copy(
                                schedule = scheduleWithEndTime,
                                timeSelectionDialogType = null
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                schedule = schedule.copy(endTime = null),
                                timeSelectionDialogType = null
                            )
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
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
<<<<<<< Updated upstream
}
=======
<<<<<<< HEAD
=======
}
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes
