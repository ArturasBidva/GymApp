package com.example.gymapp.ui.screens.workoutschedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gymapp.data.db.models.local.Schedule
import com.example.gymapp.ui.screens.mainscreen.TestHeader
import com.example.gymapp.ui.screens.workout.WorkoutViewModel
import com.example.gymapp.ui.screens.workoutschedule.components.AddWorkoutToSchedule
import com.example.gymapp.ui.screens.workoutschedule.components.SimpleCalendarTitle
import com.example.gymapp.ui.screens.workoutschedule.components.WorkoutScheduleEvents
import com.example.gymapp.util.rememberFirstMostVisibleMonth
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth


@Composable
fun WorkoutScheduleScreen(
    viewModel: WorkoutScheduleViewModel,
    workoutViewModel: WorkoutViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val workoutUiState by workoutViewModel.uiState.collectAsState()
    Content(
        workoutScheduleUiState = uiState,
        deleteSchedule = viewModel::deleteSchedule,
        onDaySelection = viewModel::onCalendarDateSelection,
        onEditScheduleSelect = viewModel::onEditScheduleSelect
    )
    if (uiState.isDialogVisible) {
        AddWorkoutToSchedule(
            workoutUiState = workoutUiState,
            workoutScheduleUiState = uiState,
            onWorkoutSelect = viewModel::onWorkoutSelect,
            workoutScheduleDialogVisibility = viewModel::setWorkoutScheduleDialogVisibility,
            workoutScheduleDateDialogVisibility = viewModel::setWorkoutScheduleDateDialogVisibility,
            createWorkoutSchedule = viewModel::createSchedule,
            updateSchedule = viewModel::updateSchedule,
            onTimeConfirm = viewModel::onTimeConfirmation,
            onTimeValidation = viewModel::validateSelectedTime,
            onTimePickerDismiss = viewModel::onTimePickerDismiss,
            onOpenTimePickerClick = viewModel::onOpenTimePickerClick
        )
    }
    if (uiState.isEditMode) {
        AddWorkoutToSchedule(
            workoutUiState = workoutUiState,
            workoutScheduleUiState = uiState,
            onWorkoutSelect = viewModel::onWorkoutSelect,
            workoutScheduleDialogVisibility = viewModel::setWorkoutScheduleDialogVisibility,
            workoutScheduleDateDialogVisibility = viewModel::setWorkoutScheduleDateDialogVisibility,
            createWorkoutSchedule = viewModel::createSchedule,
            updateSchedule = viewModel::updateSchedule,
            onTimeConfirm = viewModel::onTimeConfirmation,
            onTimeValidation = viewModel::validateSelectedTime,
            onTimePickerDismiss = viewModel::onTimePickerDismiss,
            onOpenTimePickerClick = viewModel::onOpenTimePickerClick
        )
    }
}

@Composable
private fun Content(
    workoutScheduleUiState: WorkoutScheduleUiState,
    deleteSchedule: (Long) -> Unit,
    onEditScheduleSelect: (Schedule) -> Unit,
    onDaySelection: (LocalDate?) -> Unit
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    val daysOfWeek = remember { daysOfWeek() }

<<<<<<< Updated upstream
    val scheduleSelectedDays = workoutScheduleUiState.schedules
        .map { it.date.toEpochDay() }

=======
<<<<<<< HEAD
=======
    val scheduleSelectedDays = workoutScheduleUiState.schedules
        .map { it.date.toEpochDay() }

>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            val scheduleSelectedDays = workoutScheduleUiState.schedules
                .filter { it.date == workoutScheduleUiState.selectedCalendarDate }

            TestHeader(text = "Workout schedule")
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
            ) {
                val calendarState = rememberCalendarState(
                    startMonth = startMonth,
                    endMonth = endMonth,
                    firstVisibleMonth = currentMonth,
                    firstDayOfWeek = daysOfWeek.first(),
                )
                val coroutineScope = rememberCoroutineScope()
                val visibleMonth =
                    rememberFirstMostVisibleMonth(calendarState, viewportPercent = 90f)
                LaunchedEffect(visibleMonth) {
                    // Clear selection if we scroll to a new month.
                    onDaySelection(null)
                }
                SimpleCalendarTitle(
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
                    currentMonth = visibleMonth.yearMonth,
                    goToPrevious = {
                        coroutineScope.launch {
                            calendarState.animateScrollToMonth(calendarState.firstVisibleMonth.yearMonth.previousMonth)
                        }
                    },
                    goToNext = {
                        coroutineScope.launch {
                            calendarState.animateScrollToMonth(calendarState.firstVisibleMonth.yearMonth.nextMonth)
                        }
                    },
                )
                WorkoutScheduleCalendar(
                    workoutScheduleUiState = workoutScheduleUiState,
                    calendarState = calendarState,
                    onDaySelection = {
                        onDaySelection(it.date)
                    },
                    daysOfWeek = daysOfWeek,
                    schedules = workoutScheduleUiState.schedules
                )
                    Divider(modifier = Modifier.height(10.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
                    items(items = scheduleSelectedDays) {schedule ->
                            WorkoutScheduleEvents(
                                schedule = schedule,
=======
>>>>>>> Stashed changes
                    items(items = scheduleSelectedDays) { schedule ->
                        val schedulesForDay = workoutScheduleUiState.schedules.filter {
                            it.date.toEpochDay() == schedule
                        }
                        schedulesForDay.firstOrNull()?.let {
                            WorkoutScheduleEvents(
                                schedule = it,
<<<<<<< Updated upstream
=======
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes
                                deleteSchedule = deleteSchedule,
                                setScheduleForEdit = onEditScheduleSelect
                            )
                        }
                    }
                }
            }
        }
    }


@Preview
@Composable
fun WorkoutSchedulePreview() {
    Content(
        workoutScheduleUiState = WorkoutScheduleUiState(),
        deleteSchedule = {},
        onDaySelection = {},
        onEditScheduleSelect = {}
    )
}