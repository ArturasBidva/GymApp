package com.example.gymapp.ui.screens.workoutschedule.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.R
import com.example.gymapp.data.local.WorkoutLocal
import com.example.gymapp.ui.quicksandBold
import com.example.gymapp.ui.quicksandMedium
import com.example.gymapp.ui.screens.workoutschedule.TimeSelectionDialogType
import com.example.gymapp.ui.screens.workoutschedule.WorkoutScheduleUiState
import com.example.gymapp.util.MockWorkoutLocalData
import com.example.gymapp.util.toFormattedString
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun WorkoutScheduleEvents(
    workout: WorkoutLocal,
    selectedDay: LocalDate?,
    deleteWorkoutSchedule: (WorkoutLocal, LocalDate) -> Unit,
    setWorkoutForEdit: (WorkoutLocal?) -> Unit,
) {
    selectedDay?.let { day ->
        val schedulesForDay = workout.schedules?.filter { schedule ->
            schedule.date?.let { LocalDate.ofEpochDay(it.toEpochDay()) } == day
        }
        val delete = SwipeAction(
            onSwipe = { deleteWorkoutSchedule(workout, selectedDay) },
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
            onSwipe = { setWorkoutForEdit(workout) },
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

        SwipeableActionsBox(
            startActions = listOf(edit),
            endActions = listOf(delete)
        ) {
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
                            text = workout.title,
                            fontSize = 16.sp,
                            fontFamily = quicksandBold,
                            color = Color(0xFF222B45)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = workout.description,
                            fontSize = 12.sp,
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
fun CalendarEventBoxPreview() {
    val deleteWorkoutSchedule: (WorkoutLocal, LocalDate) -> Unit =
        { workout: WorkoutLocal, schedule: LocalDate ->
            println("Deleting workout schedule at time: $workout with details: $schedule")
        }

    WorkoutScheduleEvents(
        workout = MockWorkoutLocalData.mockWorkoutsLocal[0],
        selectedDay = null,
        deleteWorkoutSchedule = deleteWorkoutSchedule,
        setWorkoutForEdit = {}
    )
}