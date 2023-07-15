package com.example.gymapp.ui.screens.createexercise

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.domain.exercises.ExerciseCategory
import com.example.gymapp.domain.exercises.ExerciseEvent
import com.example.gymapp.domain.exercises.ExerciseState
import com.example.gymapp.ui.screens.exercise.CreateExerciseViewModel
import com.example.gymapp.ui.screens.exercise.ExerciseViewModel
import com.example.gymapp.util.MockExerciseData.mockExerciseState
import com.example.gymapp.util.Resource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateExerciseScreen(
    viewModel: ExerciseViewModel,
    createExerciseViewModel: CreateExerciseViewModel,
    state: ExerciseState,
    onEvent: (ExerciseEvent) -> Unit,
    onNavigateBack: () -> Unit,
    snackbarHostState: SnackbarHostState
) {

    val exerciseCategories by createExerciseViewModel
        .exerciseCategories.collectAsState(Resource.Loading())
    val message by viewModel.eventFlow.collectAsState(initial = null)


    if (message != null) {
        val snackbarMessage = message!!.asString()
        Log.d("amogus", snackbarMessage)
        LaunchedEffect(key1 = message) {
            snackbarHostState.showSnackbar(message = snackbarMessage)
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.padding(bottom = 60.dp)
    ) {
        it
        val categories = exerciseCategories.data
        if (categories != null) {
            ExerciseExpandContent(
                categories = categories,
                onNavigateBack = onNavigateBack,
                state = state,
                onEvent = onEvent
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseExpandContent(
    state: ExerciseState,
    categories: List<ExerciseCategory>,
    onNavigateBack: () -> Unit,
    onEvent: (ExerciseEvent) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedExerciseCategories by remember {
        mutableStateOf<List<ExerciseCategory>>(
            emptyList()
        )
    }


    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Create Exercise",
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
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
                            modifier = Modifier
                                .background(Color.White)
                                .width(224.dp)
                        ) {
                            TextField(
                                value = if (selectedExerciseCategories.isNotEmpty()) {
                                    selectedExerciseCategories.joinToString { it.category }
                                } else {
                                    "Select exercise category"
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
                                categories.forEach { exerciseCategory ->
                                    val isSelected =
                                        exerciseCategory in selectedExerciseCategories
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                selectedExerciseCategories = if (isSelected) {
                                                    selectedExerciseCategories.filter { selectedExerciseCategory ->
                                                        selectedExerciseCategory != exerciseCategory
                                                    }
                                                } else {
                                                    selectedExerciseCategories + exerciseCategory
                                                }
                                                onEvent(
                                                    ExerciseEvent.SetCategory(
                                                        selectedExerciseCategories
                                                    )
                                                )
                                                expanded = !expanded
                                            }
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = exerciseCategory.category)
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

                    Spacer(modifier = Modifier.padding(bottom = 17.dp))

                    CustomTextField(
                        input = state.title, label = "Exercise title"
                    ) { onEvent(ExerciseEvent.SetTitle(it)) }
                    Spacer(modifier = Modifier.padding(bottom = 17.dp))
                    CustomTextField(
                        input = state.imgUrl, label = "Exercise image"
                    ) { onEvent(ExerciseEvent.SetImgUrl(it)) }
                    Spacer(modifier = Modifier.padding(bottom = 17.dp))
                    CustomTextField(
                        input = state.description, label = "Exercise description"
                    ) { onEvent(ExerciseEvent.SetDescription(it)) }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = {
                            Log.d("CreateExerciseScreen", "Save Exercise button clicked")
                            onEvent(ExerciseEvent.SaveExercise)
                            Log.d("CreateExerciseScreen", "Save Exercise event triggered")
                            selectedExerciseCategories = emptyList()
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

@Composable
fun ExerciseCategorySelection(
) {
    var selectedExerciseCategory by remember {
        mutableStateOf((1..20).map {
            ExerciseCategory(
                category = "$ $it", isSelected = false
            )
        })
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(selectedExerciseCategory.size) { i ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    selectedExerciseCategory =
                        selectedExerciseCategory.mapIndexed { index, exerciseCategory ->
                            if (i == index) {
                                exerciseCategory.copy(isSelected = !exerciseCategory.isSelected)
                            } else exerciseCategory
                        }
                }
                .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = selectedExerciseCategory[i].category)
                if (selectedExerciseCategory[i].isSelected) {
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

@Preview
@Composable
fun ExerciseCategorySelectionPrev() {
    ExerciseCategorySelection()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(input: String, label: String, onValueChange: (String) -> Unit) {
    TextField(
        value = input,
        singleLine = true,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        placeholder = { Text(text = "Enter Title") },
        modifier = Modifier
            .width(224.dp)
            .clip(RoundedCornerShape(10.dp)),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            disabledTextColor = Color.Transparent,
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(input: Int, label: String, onValueChange: (Int) -> Unit, size: Dp) {
    TextField(
        value = input.toString(),
        singleLine = true,
        onValueChange = { onValueChange(it.toIntOrNull() ?: 0) },
        label = { Text(text = label, fontSize = 10.sp) },
        placeholder = { Text(text = "Enter Title") },
        modifier = Modifier
            .width(size)
            .clip(RoundedCornerShape(10.dp)),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            disabledTextColor = Color.Transparent,
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Preview
@Composable
fun CreateExerciseScreenPreview() {
    MaterialTheme() {
        ExerciseExpandContent(categories = listOf(
            ExerciseCategory(category = "Antras"), ExerciseCategory(category = "Trecias")
        ), onNavigateBack = {}, state = mockExerciseState, onEvent = {})
    }
}
