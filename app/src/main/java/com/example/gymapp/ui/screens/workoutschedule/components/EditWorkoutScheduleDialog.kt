package com.example.gymapp.ui.screens.workoutschedule.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.example.gymapp.R
import com.example.gymapp.data.local.WorkoutLocal
import com.example.gymapp.ui.quicksandBold
import com.example.gymapp.ui.quicksandMedium
import com.example.gymapp.ui.screens.createworkout.CustomTextField
import com.example.gymapp.ui.screens.workoutschedule.TimeSelectionDialogType
import com.example.gymapp.ui.screens.workoutschedule.WorkoutScheduleUiState
import com.example.gymapp.util.MockWorkoutLocalData
import com.example.gymapp.util.toFormattedString
import java.time.LocalDate
import java.time.LocalTime

@SuppressLint("UnrememberedMutableState")
@Composable
fun EditWorkoutScheduleDialog(
    workout: WorkoutLocal,
    selectedDay: LocalDate,
    workoutScheduleUiState: WorkoutScheduleUiState,
    onTimeValidation: (Long) -> Boolean,
    onTimePickerDismiss: () -> Unit,
    onTimeConfirm: (LocalTime) -> Unit,
    onOpenTimePickerClick: (TimeSelectionDialogType) -> Unit,
    selectWorkoutDate: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    workoutScheduleDateDialogVisibility: (Boolean) -> Unit,
    createWorkoutSchedule: () -> Unit,
    selectColor: (Color) -> Unit,
) {
    var workoutNote by remember {
        mutableStateOf("")
    }

    Log.d("amogusas", workoutScheduleUiState.selectedColor.toString())
    workoutScheduleUiState.timeSelectionDialogType?.let {
        TimePickerDialog(
            onDismiss = onTimePickerDismiss,
            onTimeConfirm = onTimeConfirm,
            workoutTime = workoutScheduleUiState.getTimeSelectionTime()
        )
    }
    if (workoutScheduleUiState.isCalendarDialogVisible) {
        WorkoutDatePickerDialog(
            onTimeValidation = onTimeValidation,
            selectedDate = { selectWorkoutDate(it) },
            dialogVisibility = { workoutScheduleDateDialogVisibility(it) },
            selectedDateCallback = { it })
    }
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            securePolicy = SecureFlagPolicy.SecureOff
        ),
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(32.dp))
                Text(
                    "Update Event",
                    fontFamily = quicksandMedium,
                    fontSize = 20.sp,
                    color = Color(0xFF222B45)
                )
                workout.schedules?.filter { selectedDay == it.date }?.let { it ->
                    workoutScheduleUiState.selectedTimeStart
                    Spacer(modifier = Modifier.height(21.dp))
                    CustomTextField(
                        value = workout.title,
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .height(50.dp)
                            .padding(horizontal = 16.dp)
                            .border(
                                border = BorderStroke(1.dp, Color(0xFFEDF1F7)),
                                shape = RoundedCornerShape(10.dp)
                            )
                    )
                    Spacer(Modifier.height(15.dp))
                    ColorPicker(selectColor)
                    Spacer(modifier = Modifier.height(15.dp))
                    CustomTextField(
                        value = workoutNote,
                        hintText = "Workout note...",
                        onValueChange = { workoutNote = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .height(90.dp)
                            .padding(horizontal = 16.dp)
                            .border(
                                border = BorderStroke(1.dp, Color(0xFFEDF1F7)),
                                shape = RoundedCornerShape(10.dp)
                            )
                    )
                    Spacer(Modifier.height(15.dp))
                    CustomTextField(
                        value = it.joinToString { it.date.toFormattedString() },
                        isDisabled = true,
                        hintText = "Date",
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .height(50.dp)
                            .padding(horizontal = 16.dp)
                            .border(
                                border = BorderStroke(1.dp, Color(0xFFEDF1F7)),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                workoutScheduleDateDialogVisibility(true)
                            }, painterId = R.drawable.outline_calendar_today_24
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 17.dp),
                    ) {
                        Box(Modifier.weight(1f)) {
                            CustomTextField(
                                value = if (workoutScheduleUiState.selectedTimeStart == null) it.joinToString { it.startTime.toFormattedString() } else workoutScheduleUiState.selectedTimeStart.toFormattedString(),
                                isDisabled = true,
                                hintText = "Start time",
                                onValueChange = {},
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .height(50.dp)
                                    .clickable {
                                        onOpenTimePickerClick(TimeSelectionDialogType.StartTimeUpdate)
                                    }
                                    .border(
                                        border = BorderStroke(1.dp, Color(0xFFEDF1F7)),
                                        shape = RoundedCornerShape(10.dp)
                                    ),
                                painterId = R.drawable.baseline_access_time_24
                            )

                        }
                        Box(Modifier.weight(1f)) {
                            CustomTextField(
                                value = it.joinToString { it.endTime.toFormattedString() },
                                isDisabled = true,
                                hintText = "End time",
                                onValueChange = {},
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .height(50.dp)
                                    .clickable {
                                        onOpenTimePickerClick(TimeSelectionDialogType.EndTime)
                                    }
                                    .border(
                                        border = BorderStroke(1.dp, Color(0xFFEDF1F7)),
                                        shape = RoundedCornerShape(10.dp)
                                    ),
                                painterId = R.drawable.baseline_access_time_24
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Remind me",
                        modifier = Modifier.weight(1f),
                        fontSize = 14.sp,
                        fontFamily = quicksandMedium
                    )
                    val mCheckedState = remember { mutableStateOf(false) }
                    Spacer(modifier = Modifier.width(80.dp))
                    Switch(
                        checked = mCheckedState.value,
                        onCheckedChange = { mCheckedState.value = it },
                        modifier = Modifier
                            .scale(scale = 0.5f)
                            .weight(1f)
                    )
                }
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 19.dp)
                        .clickable {
                            workoutScheduleUiState.selectedWorkout?.let {
                                createWorkoutSchedule()
                            }
                        }
                        .clip(RoundedCornerShape(7.dp))
                        .background(Color(0xFF9FE7F5)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Update event",
                        fontFamily = quicksandBold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

@Preview
@Composable
fun UpdateWorkoutScheduleDialogPreview() {
    EditWorkoutScheduleDialog(
        workoutScheduleUiState = WorkoutScheduleUiState(),
        onTimeValidation = { _ -> true },
        onTimePickerDismiss = { /*TODO*/ },
        onTimeConfirm = {},
        onOpenTimePickerClick = {},
        onDismiss = {},
        selectWorkoutDate = {},
        workoutScheduleDateDialogVisibility = {},
        createWorkoutSchedule = {},
        selectColor = {},
        workout = MockWorkoutLocalData.mockWorkoutsLocal[0],
        selectedDay = LocalDate.now(),
    )
}