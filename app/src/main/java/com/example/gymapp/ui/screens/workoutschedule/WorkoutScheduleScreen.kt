package com.example.gymapp.ui.screens.workoutschedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gymapp.R
import com.example.gymapp.domain.workouts.Workout
import com.example.gymapp.ui.screens.mainscreen.TestHeader
import com.example.gymapp.ui.screens.workout.WorkoutUiState
import com.example.gymapp.ui.screens.workout.WorkoutViewModel
import com.example.gymapp.ui.screens.workoutschedule.components.AddWorkoutToSchedule
import com.example.gymapp.util.rememberFirstMostVisibleMonth
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth


@Composable
fun WorkoutScheduleScreen(
    workoutScheduleViewModel: WorkoutScheduleViewModel,
    workoutViewModel: WorkoutViewModel = hiltViewModel()
) {
    val uiState by workoutScheduleViewModel.uiState.collectAsState()
    val workoutUiState by workoutViewModel.uiState.collectAsState()
    Content(
        onTimeValidation = workoutScheduleViewModel::validateSelectedTime,
        workoutScheduleState = uiState,
        workoutState = workoutUiState,
        onTimeConfirm = workoutScheduleViewModel::onTimeConfirmation,
        onTimePickerDismiss = workoutScheduleViewModel::onTimePickerDismiss,
        onOpenTimePickerClick = workoutScheduleViewModel::onOpenTimePickerClick,
        selectedWorkout = workoutScheduleViewModel::selectWorkout,
        workoutScheduleVisibility = workoutScheduleViewModel::setWorkoutScheduleDialogVisibility,
        selectedWorkoutDate = workoutScheduleViewModel::selectWorkoutDate,
        workoutScheduleDateVisibility = workoutScheduleViewModel::setWorkoutScheduleDateDialogVisibility,
        createWorkoutSchedule = workoutScheduleViewModel::addWorkoutToSchedule
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    onTimeValidation: (Long) -> Boolean,
    workoutScheduleState: WorkoutScheduleUiState,
    workoutState: WorkoutUiState,
    onTimePickerDismiss: () -> Unit,
    onTimeConfirm: (LocalTime) -> Unit,
    onOpenTimePickerClick: (TimeSelectionDialogType) -> Unit,
    selectedWorkout: (Workout) -> Unit,
    workoutScheduleVisibility: (Boolean) -> Unit,
    selectedWorkoutDate: (LocalDate) -> Unit,
    workoutScheduleDateVisibility: (Boolean) -> Unit,
    createWorkoutSchedule: (Workout) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            TestHeader(text = "Workout schedule")
            Spacer(modifier = Modifier.height(10.dp))
            val currentMonth = remember { YearMonth.now() }
            val startMonth = remember { currentMonth.minusMonths(500) }
            val endMonth = remember { currentMonth.plusMonths(500) }
            val selections = remember { mutableStateListOf<CalendarDay>() }
            val daysOfWeek = remember { daysOfWeek() }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
            ) {
                val state = rememberCalendarState(
                    startMonth = startMonth,
                    endMonth = endMonth,
                    firstVisibleMonth = currentMonth,
                    firstDayOfWeek = daysOfWeek.first(),
                )
                val coroutineScope = rememberCoroutineScope()
                val visibleMonth = rememberFirstMostVisibleMonth(state, viewportPercent = 90f)
                SimpleCalendarTitle(
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
                    currentMonth = visibleMonth.yearMonth,
                    goToPrevious = {
                        coroutineScope.launch {
                            state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                        }
                    },
                    goToNext = {
                        coroutineScope.launch {
                            state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                        }
                    },
                )
                HorizontalCalendar(
                    modifier = Modifier.testTag("Calendar"),
                    state = state,
                    dayContent = { day ->
                        Day(day, isSelected = selections.contains(day)) { clicked ->
                            if (selections.contains(clicked)) {
                                selections.remove(clicked)
                            } else {
                                selections.add(clicked)
                            }
                        }
                    },
                    monthHeader = {
                        MonthHeader(daysOfWeek = daysOfWeek)
                    },
                )
            }
        }

            if (workoutScheduleState.isDialogVisible) {
                AddWorkoutToSchedule(
                    workoutUiState = workoutState,
                    workoutScheduleUiState = workoutScheduleState,
                    onTimeValidation = onTimeValidation,
                    onTimeConfirm = onTimeConfirm,
                    onTimePickerDismiss = onTimePickerDismiss,
                    onOpenTimePickerClick = onOpenTimePickerClick,
                    selectedWorkout = selectedWorkout,
                    workoutScheduleDialogVisibility = workoutScheduleVisibility,
                    selectWorkoutDate = selectedWorkoutDate,
                    workoutScheduleDateDialogVisibility = workoutScheduleDateVisibility,
                    createWorkoutSchedule = createWorkoutSchedule
                )
            }
        }
    }


@Composable
private fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("MonthHeader"),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                text = dayOfWeek.name,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Preview
@Composable
fun WorkoutSchedulePreview() {
    Content(
        onTimeValidation = { _ -> true },
        workoutScheduleState = WorkoutScheduleUiState(),
        workoutState = WorkoutUiState(),
        onTimePickerDismiss = {},
        onTimeConfirm = {},
        onOpenTimePickerClick = {},
        selectedWorkout = {},
        workoutScheduleDateVisibility = {},
        workoutScheduleVisibility = {},
        selectedWorkoutDate = {},
        createWorkoutSchedule = {}
    )
}

@Composable
private fun Day(day: CalendarDay, isSelected: Boolean, onClick: (CalendarDay) -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .testTag("MonthDay")
            .padding(6.dp)
            .clip(CircleShape)
            .background(color = if (isSelected) Color(0xFFFF9B70) else Color.Transparent)
            // Disable clicks on inDates/outDates
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        val textColor = when (day.position) {
            // Color.Unspecified will use the default text color from the current theme
            DayPosition.MonthDate -> if (isSelected) Color.White else Color.Unspecified
            DayPosition.InDate, DayPosition.OutDate -> colorResource(R.color.teal_700)
        }
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 14.sp,
        )
    }
}