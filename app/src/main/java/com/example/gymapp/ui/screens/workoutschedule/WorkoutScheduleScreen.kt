package com.example.gymapp.ui.screens.workoutschedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gymapp.R
import com.example.gymapp.data.local.WorkoutLocal
import com.example.gymapp.ui.quicksandBold
import com.example.gymapp.ui.quicksandMedium
import com.example.gymapp.ui.screens.mainscreen.TestHeader
import com.example.gymapp.ui.screens.workout.WorkoutUiState
import com.example.gymapp.ui.screens.workout.WorkoutViewModel
import com.example.gymapp.ui.screens.workoutschedule.components.AddWorkoutToSchedule
import com.example.gymapp.ui.screens.workoutschedule.components.SimpleCalendarTitle
import com.example.gymapp.util.MockWorkoutData
import com.example.gymapp.util.MockWorkoutLocalData
import com.example.gymapp.util.displayText
import com.example.gymapp.util.rememberFirstMostVisibleMonth
import com.example.gymapp.util.toFormattedString
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
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
        createWorkoutSchedule = workoutScheduleViewModel::addWorkoutToSchedule,
        selectColor = workoutScheduleViewModel::selectColor,
        deleteWorkoutSchedule = workoutScheduleViewModel::deleteWorkoutSchedule
    )
}

@Composable
private fun Content(
    onTimeValidation: (Long) -> Boolean,
    workoutScheduleState: WorkoutScheduleUiState,
    workoutState: WorkoutUiState,
    onTimePickerDismiss: () -> Unit,
    onTimeConfirm: (LocalTime) -> Unit,
    onOpenTimePickerClick: (TimeSelectionDialogType) -> Unit,
    selectedWorkout: (WorkoutLocal) -> Unit,
    workoutScheduleVisibility: (Boolean) -> Unit,
    selectedWorkoutDate: (LocalDate) -> Unit,
    workoutScheduleDateVisibility: (Boolean) -> Unit,
    createWorkoutSchedule: (WorkoutLocal) -> Unit,
    selectColor: (Color) -> Unit,
    deleteWorkoutSchedule: (WorkoutLocal, LocalDate) -> Unit
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
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TestHeader(text = "Workout schedule")
            Spacer(modifier = Modifier.height(10.dp))
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
                LaunchedEffect(visibleMonth) {
                    // Clear selection if we scroll to a new month.
                    selection = null
                }
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
                    modifier = Modifier.wrapContentWidth(),
                    state = state,
                    dayContent = { day ->
                        val colors = if (day.position == DayPosition.MonthDate) {
                            val dayEpoch = day.date.toEpochDay()
                            val workoutsForDay = workoutDays[dayEpoch].orEmpty()
                            val dayColors = workoutsForDay.flatMap { workout ->
                                workout.schedules?.filter { schedule ->
                                    schedule.date?.let { LocalDate.ofEpochDay(it.toEpochDay()) } == day.date
                                }?.mapNotNull { schedule ->
                                    schedule.color?.let { Color(it) }
                                } ?: emptyList()
                            }
                            dayColors
                        } else {
                            emptyList()
                        }
                        Day(
                            day = day,
                            isSelected = workoutScheduleState.selectedDay == day.date,
                            colors = colors,
                        ) { selectedDay ->
                            workoutScheduleState.selectedDay = selectedDay.date
                            selection = selectedDay
                        }
                    },
                    monthHeader = {
                        MonthHeader(daysOfWeek = daysOfWeek)
                    }
                )
                Divider(modifier = Modifier.height(40.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(items = workoutSelectedDays) { workout ->
                        WorkoutInformation(
                            workout,
                            selectedDay = workoutScheduleState.selectedDay,
                            deleteWorkoutSchedule = deleteWorkoutSchedule,
                            workoutScheduleState = workoutScheduleState
                        )
                    }
                }
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
                createWorkoutSchedule = createWorkoutSchedule,
                selectColor = selectColor
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
                text = dayOfWeek.displayText(),
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    colors: List<Color> = emptyList(),
    onClick: (CalendarDay) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .testTag("MonthDay")
            .padding(start = 6.dp, end = 6.dp, bottom = 6.dp)
            .clip(CircleShape)
            .background(color = if (isSelected) Color(0xFFFCCA3E) else Color.Transparent)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val textColor = when (day.position) {
                DayPosition.MonthDate -> Color.Unspecified
                DayPosition.InDate, DayPosition.OutDate -> Color(0xFFBEBEBE)
            }

            Spacer(modifier = Modifier.height(12.dp))
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.date.dayOfMonth.toString(),
                    color = textColor,
                    fontSize = 14.sp,
                )
            }

            // Spacer to create space between the number and the box
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                if (colors.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        colors.forEach { color ->
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .padding(
                                        start = 2.dp,
                                        end = 2.dp,
                                        top = 0.dp
                                    ) // Adjust spacing if needed
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WorkoutInformation(
    workout: WorkoutLocal,
    selectedDay: LocalDate?,
    deleteWorkoutSchedule: (WorkoutLocal, LocalDate) -> Unit,
    workoutScheduleState: WorkoutScheduleUiState
) {
    CalendarEventBox(
        workout = workout,
        selectedDay = selectedDay,
        deleteWorkoutSchedule = deleteWorkoutSchedule,
        workoutScheduleState = workoutScheduleState
    )
}


@Composable
fun CalendarEventBox(
    workout: WorkoutLocal,
    selectedDay: LocalDate?,
    deleteWorkoutSchedule: (WorkoutLocal, LocalDate) -> Unit,
    workoutScheduleState: WorkoutScheduleUiState
) {
    selectedDay?.let { day ->
        val schedulesForDay = workout.schedules?.filter { schedule ->
            schedule.date?.let { LocalDate.ofEpochDay(it.toEpochDay()) } == day
        }
        val delete = SwipeAction(
            onSwipe = {
                deleteWorkoutSchedule(workout, selectedDay)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            },
            background = Color.Red
        )

        val edit = SwipeAction(
            onSwipe = {
                workoutScheduleState.isCalendarDialogVisible
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            },
            background = Color.Green
        )

        if(workoutScheduleState.isDialogVisible){

        }
        SwipeableActionsBox(endActions = listOf(delete), startActions = listOf(edit)) {

            schedulesForDay?.forEach { schedule ->

                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(Color((schedule.color ?: Color.Black) as Int))
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "${schedule.startTime.toFormattedString()} - ${schedule.endTime.toFormattedString()}",
                                fontSize = 12.sp,
                                fontFamily = quicksandMedium,
                                color = Color(0xFF8F9BB3)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Image(
                                painterResource(id = R.drawable.baseline_more_horiz_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                                    .padding(start = 8.dp)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = workout.title ?: "", fontSize = 16.sp,
                            fontFamily = quicksandBold,
                            color = Color(0xFF222B45)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = workout.description, fontSize = 12.sp,
                            fontFamily = quicksandMedium,
                            color = Color(0xFF8F9BB3)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DayPrev() {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    val daysOfWeek = remember { daysOfWeek() }
    var selection by remember { mutableStateOf<CalendarDay?>(null) }
    val workoutDays = MockWorkoutLocalData.mockWorkoutsLocal
        .flatMap { workout ->
            workout.schedules.orEmpty().map { it.date?.toEpochDay() to workout }
        }
        .groupBy({ it.first }, { it.second })
    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
    )
    HorizontalCalendar(
        modifier = Modifier.wrapContentWidth(),
        state = state,
        dayContent = { day ->

            val colors = if (day.position == DayPosition.MonthDate) {
                workoutDays[day.date.toEpochDay()].orEmpty()
                    .map {
                        colorResource(it.schedules?.firstOrNull()?.color!!)
                    }
            } else {
                emptyList()
            }
            Day(
                day = day,
                isSelected = selection == day,
                colors = colors,
            ) { selectedDay ->
                selection = selectedDay
            }
        },
        monthHeader = {
            MonthHeader(daysOfWeek = daysOfWeek)
        }
    )
}


@Preview
@Composable
fun CalendarEventBoxPreview() {
    val deleteWorkoutSchedule: (WorkoutLocal, LocalDate) -> Unit =
        { workout: WorkoutLocal, schedule: LocalDate ->
            println("Deleting workout schedule at time: $workout with details: $schedule")
        }

    CalendarEventBox(
        workout = MockWorkoutLocalData.mockWorkoutsLocal[0],
        selectedDay = null,
        deleteWorkoutSchedule = deleteWorkoutSchedule,
        workoutScheduleState = WorkoutScheduleUiState()
    )
}

@Preview
@Composable
fun WorkoutSchedulePreview() {
    val deleteWorkoutSchedule: (WorkoutLocal, LocalDate) -> Unit =
        { workoutLocal: WorkoutLocal, localDate: LocalDate ->
            println("Deleting workout schedule for workout: ${workoutLocal.title} on date: $localDate")
        }
    Content(
        onTimeValidation = { _ -> true },
        workoutScheduleState = WorkoutScheduleUiState(),
        workoutState = WorkoutUiState(workout = MockWorkoutData.mockWorkouts[1]),
        onTimePickerDismiss = {},
        onTimeConfirm = {},
        onOpenTimePickerClick = {},
        selectedWorkout = {},
        workoutScheduleVisibility = {},
        selectedWorkoutDate = {},
        workoutScheduleDateVisibility = {},
        createWorkoutSchedule = {},
        selectColor = {},
        deleteWorkoutSchedule = deleteWorkoutSchedule
    )
}
