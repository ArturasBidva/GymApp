package com.example.gymapp.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gymapp.ui.screens.exercise.ExerciseViewModel
import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.domain.workouts.ExerciseWorkouts
import com.example.gymapp.domain.workouts.Workout

@Composable
fun AddWorkoutExerciseScreen(
    exerciseViewModel: ExerciseViewModel,
    workoutViewModel: WorkoutViewModel,
    onNavigateBack: () -> Unit
) {
    val exercises = exerciseViewModel.exercises.value?.data ?: emptyList()
    val workouts by workoutViewModel.workouts.observeAsState(listOf())
    AddExerciseToWorkoutScreen(
        exercises = exercises,
        workouts = workouts,
        onNavigateBack = onNavigateBack,
        onConfirmClick = { exerciseWorkouts, workoutId ->
            exerciseWorkouts.forEach { exerciseWorkout ->
                workoutViewModel.createExerciseWorkoutAndAddToWorkout(exerciseWorkout, workoutId)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExerciseToWorkoutScreen(
    exercises: List<Exercise>,
    workouts: List<Workout>,
    onNavigateBack: () -> Unit,
    onConfirmClick: (List<ExerciseWorkouts>, Long) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var expandedTwo by remember { mutableStateOf(false) }
    var selectedExercises by remember { mutableStateOf<List<Exercise>>(emptyList()) }
    var selectedWorkout by remember { mutableStateOf(Workout("", "")) }
    var showExerciseBox by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Add exercise to workout",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(16.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(10.dp))
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = expandedTwo,
                            onExpandedChange = { expandedTwo = !expandedTwo },
                            modifier = Modifier
                                .background(Color.White)
                                .width(224.dp)
                        ) {
                            TextField(
                                value = selectedWorkout.title.ifEmpty {
                                    "Select workout"
                                },
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = expandedTwo
                                    )
                                },
                                modifier = Modifier
                                    .background(Color.White)
                                    .menuAnchor()
                                    .clip(RoundedCornerShape(10.dp)),
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.Black,
                                    containerColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                )
                            )

                            ExposedDropdownMenu(
                                expanded = expandedTwo,
                                modifier = Modifier.background(Color.White),
                                onDismissRequest = { expandedTwo = false }
                            ) {
                                workouts.forEach { workout ->
                                    val isSelected = selectedWorkout == workout
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                selectedWorkout = workout
                                                showExerciseBox = true
                                                expandedTwo = false
                                            }
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = workout.title)
                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Selected",
                                                tint = Color.Green,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (showExerciseBox) {
                        Box(modifier = Modifier.clip(RoundedCornerShape(10.dp)))
                        {
                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = { expanded = !expanded },
                                modifier = Modifier
                                    .background(Color.White)
                                    .width(224.dp)
                            ) {
                                TextField(
                                    value = if (selectedExercises.isNotEmpty()) {
                                        selectedExercises.joinToString { it.title }
                                    } else {
                                        "Select exercise"
                                    },
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expanded
                                        )
                                    },
                                    modifier = Modifier
                                        .background(Color.White)
                                        .menuAnchor()
                                        .clip(RoundedCornerShape(10.dp)),
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = Color.Black,
                                        containerColor = Color.White,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        disabledIndicatorColor = Color.Transparent
                                    )
                                )

                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    modifier = Modifier.background(Color.White),
                                    onDismissRequest = { expanded = false }
                                ) {
                                    val existingExercises =
                                        selectedWorkout.exerciseWorkouts.map { it.exercise }
                                    val filteredExercises =
                                        exercises.filter { it !in existingExercises }
                                    filteredExercises.forEach { exercise ->
                                        val isSelected = exercise in selectedExercises
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    selectedExercises = if (isSelected) {
                                                        selectedExercises.filter { selectedExerciseCategory ->
                                                            selectedExerciseCategory != exercise
                                                        }
                                                    } else {
                                                        selectedExercises + exercise
                                                    }
                                                }
                                                .padding(16.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(text = exercise.title)
                                            if (isSelected) {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = "Selected",
                                                    tint = Color.Green,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.padding(bottom = 17.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = {
                            val exerciseWorkoutsList = selectedExercises.map { exercise ->
                                ExerciseWorkouts(
                                    0,
                                    exercise,
                                    0,
                                    0,
                                    0
                                )
                            }
                            onConfirmClick(exerciseWorkoutsList, selectedWorkout.id)


                        }) {
                            Text("Confirm")
                        }

                        Button(onClick = { onNavigateBack() }) {
                            Text("Go Back")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AddExerciseToWorkoutPreview() {
    val exercise = Exercise(0,"kazkas", 0, "belekas", "haha", listOf())
    val workout = Workout("kazkas", "belekas")
    AddExerciseToWorkoutScreen(
        exercises = listOf(exercise),
        workouts = listOf(workout),
        onNavigateBack = {},
        onConfirmClick = { _, _ -> }
    )
}