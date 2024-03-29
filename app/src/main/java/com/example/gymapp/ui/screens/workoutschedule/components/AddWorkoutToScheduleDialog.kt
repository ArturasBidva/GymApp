package com.example.gymapp.ui.screens.workoutschedule.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.example.gymapp.R
import com.example.gymapp.data.db.models.local.Schedule
import com.example.gymapp.data.db.models.local.WorkoutLocal
import com.example.gymapp.ui.quicksandBold
import com.example.gymapp.ui.quicksandMedium
import com.example.gymapp.ui.screens.createworkout.CustomTextField
import com.example.gymapp.ui.screens.workout.WorkoutUiState
import com.example.gymapp.ui.screens.workoutschedule.TimeSelectionDialogType
import com.example.gymapp.ui.screens.workoutschedule.WorkoutScheduleUiState
import com.example.gymapp.util.toFormattedString
import java.time.LocalTime

@SuppressLint("UnrememberedMutableState")
@Composable
fun AddWorkoutToSchedule(
    workoutUiState: WorkoutUiState,
    workoutScheduleUiState: WorkoutScheduleUiState,
    onTimeValidation: (Long) -> Boolean,
    onTimePickerDismiss: () -> Unit,
    onTimeConfirm: (LocalTime) -> Unit,
    onOpenTimePickerClick: (TimeSelectionDialogType) -> Unit,
    onWorkoutSelect: (WorkoutLocal) -> Unit,
    workoutScheduleDialogVisibility: (Boolean) -> Unit,
    workoutScheduleDateDialogVisibility: (Boolean) -> Unit,
    createWorkoutSchedule: () -> Unit,
    updateSchedule: (Schedule) -> Unit
) {
    var workoutNote by remember {
        mutableStateOf("")
    }
    val workout =
        workoutScheduleUiState.schedule.copy(workout = workoutScheduleUiState.selectedWorkout)
    updateSchedule(workout)
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
            onDateSelect = {
                val scheduleWithDate = workoutScheduleUiState.schedule.copy(date = it)
                updateSchedule(scheduleWithDate)
            },
            dialogVisibility = { workoutScheduleDateDialogVisibility(it) },
            selectedDateCallback = {}
        )
    }
    Dialog(
        onDismissRequest = { workoutScheduleDialogVisibility(false) },
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
                    if (workoutScheduleUiState.isEditMode) "Update Event" else "Add New Event",
                    fontFamily = quicksandMedium,
                    fontSize = 20.sp,
                    color = Color(0xFF222B45)
                )
                Spacer(modifier = Modifier.height(21.dp))
                SelectWorkoutDropDown(
                    items = workoutUiState.workouts,
                    onItemSelected = { onWorkoutSelect(it) },
                    workoutScheduleState = workoutScheduleUiState
                )
                Spacer(Modifier.height(15.dp))
                ColorPicker(
                    onColorSelect = {
                        val scheduleWithColor =
                            workoutScheduleUiState.schedule.copy(color = it.toArgb())
                        updateSchedule(scheduleWithColor)
                    }
                )
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
                    value = workoutScheduleUiState.schedule.date.toFormattedString(),
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
                            value = workoutScheduleUiState.schedule.startTime.toFormattedString(),
                            isDisabled = true,
                            hintText = "Start time",
                            onValueChange = {},
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .height(50.dp)
                                .clickable {
                                    onOpenTimePickerClick(TimeSelectionDialogType.StartTime)
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
                            value = workoutScheduleUiState.schedule.endTime.toFormattedString(),
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
                            workoutScheduleUiState.selectedWorkout.let {
                                workoutScheduleDialogVisibility(false)

                                createWorkoutSchedule()
                            }
                        }
                        .clip(RoundedCornerShape(7.dp))
                        .background(
                            if (workoutScheduleUiState.isEditMode) Color(0xFF9FE7F5) else Color(
                                0xFFFF9B70
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (workoutScheduleUiState.isEditMode) "Update event" else "Create Event",
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

@Composable
fun SelectWorkoutDropDown(
    items: List<WorkoutLocal>,
    onItemSelected: (WorkoutLocal) -> Unit,
    workoutScheduleState: WorkoutScheduleUiState
) {
    var expanded by remember { mutableStateOf(false) }
    val workout = workoutScheduleState.selectedWorkout
    var selectedWorkout by remember { mutableStateOf(workout) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp)
            .border(
                border = BorderStroke(1.dp, Color(0xFFEDF1F7)),
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = { expanded = true }, enabled = !workoutScheduleState.isEditMode)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            if (workoutScheduleState.isEditMode) {
                CustomTextField(
<<<<<<< Updated upstream
                    value = workoutScheduleState.schedules.filter { it.workout.id == workout.id }.joinToString { workout.title},
=======
<<<<<<< HEAD
                    value = workout.title,
=======
                    value = workoutScheduleState.schedules.filter { it.workout.id == workout.id }.joinToString { workout.title},
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .height(50.dp)
                        .border(
                            border = BorderStroke(1.dp, Color(0xFFEDF1F7)),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    isDisabled = true
                )
            } else {
                BasicTextField(
                    value = selectedWorkout.title.takeIf { it.isNotBlank() } ?: "Select workout...",
                    onValueChange = { newTitle ->
                        selectedWorkout.let {
                            val updatedTitle = it.copy(title = newTitle)
                            onItemSelected(updatedTitle)
                        }
                    },
                    enabled = false,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.scale(0.5f)
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item.title) },
                            onClick = {
                                selectedWorkout = item
                                onItemSelected(item)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AddWorkoutToSchedulePreview() {
    AddWorkoutToSchedule(
        workoutUiState = WorkoutUiState(),
        workoutScheduleUiState = WorkoutScheduleUiState(),
        onTimeValidation = { _ -> true },
        onTimePickerDismiss = { /*TODO*/ },
        onTimeConfirm = {},
        onOpenTimePickerClick = {},
        onWorkoutSelect = {},
        workoutScheduleDialogVisibility = {},
        workoutScheduleDateDialogVisibility = {},
        createWorkoutSchedule = {},
        updateSchedule = {}
    )
}

