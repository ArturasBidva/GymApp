package com.example.gymapp.ui.screens.workoutschedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gymapp.domain.workouts.Workout
import com.example.gymapp.ui.screens.mainscreen.TestHeader
import com.example.gymapp.ui.screens.workout.WorkoutUiState
import com.example.gymapp.ui.screens.workout.WorkoutViewModel
import com.example.gymapp.ui.screens.workoutschedule.components.AddWorkoutToSchedule
import java.time.LocalDate
import java.time.LocalTime

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
            Text(
                text = "Your schedule",
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            DatePickerDialog(onDismissRequest = { /*TODO*/ }, confirmButton = { /*TODO*/ }) {
                
            }
            DatePicker(
                state = rememberDatePickerState(initialSelectedDateMillis = null, yearRange = IntRange(2023,2024)),
                dateValidator = onTimeValidation,
                title = null,
                headline = null,
                showModeToggle = false,
                modifier = Modifier.padding(vertical = 5.dp)
            )
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