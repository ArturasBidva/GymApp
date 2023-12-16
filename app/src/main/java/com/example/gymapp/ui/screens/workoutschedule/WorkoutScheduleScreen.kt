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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gymapp.data.local.WorkoutLocal
import com.example.gymapp.ui.screens.mainscreen.TestHeader
import com.example.gymapp.ui.screens.workout.WorkoutUiState
import com.example.gymapp.ui.screens.workout.WorkoutViewModel
import com.example.gymapp.ui.screens.workoutschedule.components.AddWorkoutToSchedule
import com.example.gymapp.ui.screens.workoutschedule.components.EditWorkoutScheduleDialog
import com.example.gymapp.ui.screens.workoutschedule.components.SimpleCalendarTitle
import com.example.gymapp.ui.screens.workoutschedule.components.WorkoutScheduleEvents
import com.example.gymapp.util.MockWorkoutData
import com.example.gymapp.util.rememberFirstMostVisibleMonth
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
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
        workoutState = workoutUiState,
        deleteWorkoutSchedule = viewModel::deleteWorkoutSchedule,
        setWorkoutForEdit = viewModel::setWorkoutForEdit
    )
    if (uiState.isDialogVisible) {
        AddWorkoutToSchedule(
            workoutUiState = workoutUiState,
            workoutScheduleUiState = uiState,
            onTimeValidation = viewModel::validateSelectedTime,
            onTimeConfirm = viewModel::onTimeConfirmation,
            onTimePickerDismiss = viewModel::onTimePickerDismiss,
            onOpenTimePickerClick = viewModel::onOpenTimePickerClick,
            selectedWorkout = viewModel::selectWorkout,
            workoutScheduleDialogVisibility = viewModel::setWorkoutScheduleDialogVisibility,
            selectWorkoutDate = viewModel::selectWorkoutDate,
            workoutScheduleDateDialogVisibility = viewModel::setWorkoutScheduleDialogVisibility,
            createWorkoutSchedule = viewModel::addWorkoutToSchedule,
            selectColor = viewModel::selectColor
        )
    }

    uiState.selectedEditableWorkout?.let { workoutLocal ->
        uiState.selectedDay?.let {
            EditWorkoutScheduleDialog(
                workoutScheduleUiState = uiState,
                onTimeValidation = viewModel::validateSelectedTime,
                onTimeConfirm = viewModel::onTimeConfirmation,
                onTimePickerDismiss = viewModel::onTimePickerDismiss,
                onOpenTimePickerClick = viewModel::onOpenTimePickerClick,
                onDismiss = { viewModel.setWorkoutForEdit(null) },
                selectWorkoutDate = viewModel::selectWorkoutDate,
                workoutScheduleDateDialogVisibility = viewModel::setWorkoutScheduleDialogVisibility,
                createWorkoutSchedule = viewModel::addWorkoutToSchedule,
                selectColor = viewModel::selectColor,
                workout = workoutLocal,
                selectedDay = it

            )
        }
    }
}

@Composable
private fun Content(
    workoutScheduleUiState: WorkoutScheduleUiState,
    workoutState: WorkoutUiState,
    deleteWorkoutSchedule: (WorkoutLocal, LocalDate) -> Unit,
    setWorkoutForEdit: (WorkoutLocal?) -> Unit
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    val daysOfWeek = remember { daysOfWeek() }
    var selection by remember { mutableStateOf<CalendarDay?>(null) }
    val workoutDays = workoutState.workouts
        .flatMap { workout ->
            workout.schedules?.map { it.date?.toEpochDay() to workout } ?: emptyList()
        }
        .groupBy({ it.first }, { it.second })

    val workoutSelectedDays = if (selection?.date?.toEpochDay() == null) {
        emptyList()
    } else {
        workoutDays[selection?.date?.toEpochDay()].orEmpty()
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
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
                    selection = null
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
                        workoutScheduleUiState.selectedDay = it.date
                        selection = it
                    },
                    daysOfWeek = daysOfWeek,
                    workoutDays = workoutDays
                )
                Divider(modifier = Modifier.height(40.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(items = workoutSelectedDays) { workout ->
                        WorkoutScheduleEvents(
                            workout = workout,
                            selectedDay = workoutScheduleUiState.selectedDay,
                            deleteWorkoutSchedule = deleteWorkoutSchedule,
                            setWorkoutForEdit = setWorkoutForEdit
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
    val deleteWorkoutSchedule: (WorkoutLocal, LocalDate) -> Unit =
        { workoutLocal: WorkoutLocal, localDate: LocalDate ->
            println("Deleting workout schedule for workout: ${workoutLocal.title} on date: $localDate")
        }
    Content(
        workoutScheduleUiState = WorkoutScheduleUiState(),
        workoutState = WorkoutUiState(workout = MockWorkoutData.mockWorkouts[1]),
        deleteWorkoutSchedule = deleteWorkoutSchedule,
        setWorkoutForEdit = { }
    )
}