package com.example.gymapp.ui.screens.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.gymapp.R
import com.example.gymapp.data.local.WorkoutLocal
import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.domain.exercises.ExerciseCategory
import com.example.gymapp.domain.workouts.ExerciseWorkout
import com.example.gymapp.domain.workouts.Workout
import com.example.gymapp.ui.customOrange
import com.example.gymapp.ui.montserrati
import com.example.gymapp.ui.quicksandBold
import com.example.gymapp.ui.quicksandMedium
import com.example.gymapp.ui.reusable.LoadingCircle
import com.example.gymapp.ui.screens.createworkout.CustomButton
import com.example.gymapp.ui.screens.createworkout.CustomTextField
import com.example.gymapp.ui.screens.mainscreen.Header
import com.example.gymapp.ui.screens.workout.AddButtonIcon
import com.example.gymapp.ui.screens.workout.CreateButton
import com.example.gymapp.ui.screens.workout.RemoveButtonIcon
import com.example.gymapp.ui.screens.workout.SeeMoreButton
import com.example.gymapp.ui.screens.workout.WorkoutUiEvent
import com.example.gymapp.ui.screens.workout.WorkoutUiState
import com.example.gymapp.ui.screens.workout.WorkoutViewModel
import com.example.gymapp.util.MockExerciseCategoryData
import com.example.gymapp.util.MockExerciseData
import com.example.gymapp.util.MockWorkoutData
import com.example.gymapp.util.MockWorkoutLocalData

@Composable
fun ExercisesScreen(
    exerciseViewModel: ExerciseViewModel,
    workoutViewModel: WorkoutViewModel,
    goBack: () -> Unit
) {
    val exerciseUiState by exerciseViewModel.uiState.collectAsState()
    val workoutUiState by workoutViewModel.uiState.collectAsState()

    if (exerciseUiState.isLoading || workoutUiState.isLoading) {
        LoadingCircle()
    } else {
        Content(
            exerciseUiState = exerciseUiState,
            onExerciseUiEvent = exerciseViewModel::onEvent,
            onWorkoutUiEvent = workoutViewModel::onEvent,
            workoutUiState = workoutUiState,
            onSelection = exerciseViewModel::onSelectionClick,
            onGoBackClick = goBack
        )
    }
}

@Composable
private fun Content(
    exerciseUiState: ExerciseUiState,
    onExerciseUiEvent: (ExerciseUiEvent) -> Unit,
    onWorkoutUiEvent: (WorkoutUiEvent) -> Unit,
    workoutUiState: WorkoutUiState,
    onSelection: () -> Unit,
    onGoBackClick: () -> Unit,
) {
    MaterialTheme {
        exerciseUiState.selectedExercise?.let {
            AddExerciseToWorkoutDialog(
                exercise = it,
                onDismiss = { onExerciseUiEvent(ExerciseUiEvent.CloseAddExerciseToWorkoutDialog) },
                onSave = { weight, count, exercise ->
                    val exerciseWorkout = ExerciseWorkout(
                        null,
                        0,
                        weight.toInt(),
                        count.toInt(),
                        exercise
                    )
                    onWorkoutUiEvent(
                        WorkoutUiEvent.CreateExerciseWorkout(
                            exerciseWorkout = exerciseWorkout,
                            workout = workoutUiState.selectedWorkout!!
                        ),
                    )
                    onExerciseUiEvent(ExerciseUiEvent.CloseAddExerciseToWorkoutDialog)
                }
            )
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp + 56.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(name = "Arturcikas")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(customOrange)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(25.dp))
                    val avatar = painterResource(R.drawable.arrow)
                    Image(
                        painter = avatar,
                        contentDescription = "Back arrow",
                        modifier = Modifier.clickable { onGoBackClick()},
                    )
                    Box(
                        modifier = Modifier.width(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Exercises",
                            color = Color.White,
                            fontFamily = montserrati,
                            fontSize = 24.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            workoutUiState.selectedWorkout?.let { selectedWorkout ->
                val filtered = workoutUiState.workouts.filter { it.id != selectedWorkout.id }
                WorkoutSelectionSection(
                    onExpandClick = onSelection,
                    onCollapse = onSelection,
                    expanded = exerciseUiState.selectionExpanded,
                    selectedWorkout = selectedWorkout,
                    unselectedWorkouts = filtered,
                    onSelectWorkout = { onWorkoutUiEvent(WorkoutUiEvent.SelectWorkout(it)) },
                    workoutUiState = workoutUiState
                )

                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    exerciseUiState.exercises.forEach { exercise ->
                        val filteredCategory = exercise.category.filter {
                            it.name in (exerciseUiState.selectedExerciseCategory?.name ?: it.name)
                        }

                        if (exercise.category.contains(filteredCategory.firstOrNull())) {
                            ExerciseCard(
                                exercise = exercise,
                                onAddClick = {
                                    onExerciseUiEvent(
                                        ExerciseUiEvent.SelectExercise(
                                            exercise
                                        )
                                    )
                                },
                                workout = workoutUiState.selectedWorkout,
                                onClickSeeMore = {},
                                onRemoveClick = {
                                    onWorkoutUiEvent(
                                        WorkoutUiEvent.DeleteExerciseWorkoutFromWorkout(
                                            workoutId = workoutUiState.selectedWorkout.id,
                                            exerciseId = exercise.id
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 100.dp, horizontal = 8.dp)
        ) {
            CreateButton(onCreateClick = { }, text = "Create exercise")
        }
        Box(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 54.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            ExercisesCategoryBar(
                exerciseUiState = exerciseUiState,
                exerciseCategories = exerciseUiState.exerciseCategories,
                onExerciseUiEvent = onExerciseUiEvent,
            )
        }

    }
}


@Composable
private fun WorkoutSelectionSection(
    onExpandClick: () -> Unit,
    onCollapse: () -> Unit,
    expanded: Boolean,
    selectedWorkout: WorkoutLocal,
    unselectedWorkouts: List<WorkoutLocal>,
    onSelectWorkout: (WorkoutLocal) -> Unit,
    workoutUiState: WorkoutUiState
) {
    Column(modifier = Modifier.padding(horizontal = 30.dp)) {
        WorkoutSelection(
            expanded = expanded,
            onClick = { if (expanded) onCollapse() else onExpandClick() },
            selectedWorkout = selectedWorkout
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onCollapse,
            modifier = Modifier.background(Color(0xFF4D4D4D))
        ) {
            unselectedWorkouts.forEachIndexed { index, workout ->
                if (index != 0) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        color = Color(0xFF525252)
                    )
                }
                DropdownMenuItem(
                    text = {
                        WorkoutSubSelection(
                            workout.title,
                            workout.description
                        )
                    },
                    onClick = {
                        onSelectWorkout(workout)
                        workoutUiState.workoutInfo = workout
                        onCollapse()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Preview
@Composable
fun ExercisesScreenPrev() {
    Content(
        exerciseUiState = ExerciseUiState(exercises = MockExerciseData.mockExercises),
        onExerciseUiEvent = {},
        onWorkoutUiEvent = {},
        workoutUiState = WorkoutUiState(
            workout = MockWorkoutData.mockWorkouts[1],
            workouts = MockWorkoutLocalData.mockWorkoutsLocal
        ),
        onSelection = {},
        onGoBackClick = {},
    )
}


@Composable
fun ExercisesCategoryBar(
    exerciseUiState: ExerciseUiState,
    exerciseCategories: List<ExerciseCategory>,
    onExerciseUiEvent: (ExerciseUiEvent) -> Unit = {}
) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
            .horizontalScroll(rememberScrollState())
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            exerciseCategories.forEach { category ->
                val isSelected =
                    category.id == (exerciseUiState.selectedExerciseCategory?.id ?: false)
                Box(
                    modifier = Modifier.clickable {
                        onExerciseUiEvent(ExerciseUiEvent.SelectExerciseCategory(category))
                    }, contentAlignment = Alignment.Center
                ) {
                    Text(text = category.name,
                        fontFamily = quicksandBold,
                        fontSize = 17.sp,
                        color = if (!isSelected) Color(0xFF707070)
                        else Color(0xFFF68D5F),
                        modifier = if (!isSelected) Modifier.padding(8.dp)
                        else Modifier
                            .padding(8.dp)
                            .drawBehind {
                                val strokeWidthPx = 2.dp.toPx()
                                val verticalOffset = size.height + 6.sp.toPx()
                                drawLine(
                                    color = Color(0xFFF68D5F),
                                    strokeWidth = strokeWidthPx,
                                    start = Offset(0f, verticalOffset),
                                    end = Offset(size.width, verticalOffset)
                                )
                            })
                }
            }
        }
    }
}


@Preview
@Composable
fun ExerciseCategoryBarPrev() {
    var mockData by remember { mutableStateOf(MockExerciseCategoryData.mockExerciseCategory) }
    ExercisesCategoryBar(
        exerciseUiState = ExerciseUiState(),
        exerciseCategories = mockData
    )
}

//@Preview(group = "amogus")
//@Composable
//fun ExerciseCardPrev() {
//    ExerciseCard(
//        exercise = MockExerciseData.mockExercises[0],
//        workout = MockWorkoutData.mockWorkouts[0],
//        onClickSeeMore = {},
//        onAddClick = {}) {
//
//    }
//}

@Composable
fun ExerciseCard(
    exercise: Exercise,
    workout: WorkoutLocal,
    onClickSeeMore: () -> Unit,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    val exerciseIds = workout.exerciseWorkouts.map { it.exercise.id }.toList()

    val contains = exercise.id in exerciseIds
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.deadlift),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 18.dp)
                .padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Exercise" + "  (${exercise.category.joinToString { it.name }})",
                color = customOrange,
                fontFamily = quicksandBold,
                fontSize = 12.sp
            )
            Text(
                text = exercise.title,
                color = Color.White,
                fontFamily = quicksandBold,
                fontSize = 20.sp
            )
            Text(
                text = exercise.description,
                color = Color.White,
                fontFamily = quicksandMedium,
                fontSize = 14.sp,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(50.dp))
            Row {
                if (contains) RemoveButtonIcon { onRemoveClick() } else AddButtonIcon { onAddClick() }
                Spacer(modifier = Modifier.width(10.dp))
                if (contains) Text("Remove from workout",
                    color = Color(0xFFFF1500),
                    modifier = Modifier.clickable { onRemoveClick() }) else
                    Text("Add to workout",
                        color = Color(0xFFFFAB40),
                        modifier = Modifier.clickable { onAddClick() })
                Spacer(modifier = Modifier.width(30.dp))
                SeeMoreButton(onClickSeeMore)
            }
        }
    }
}

@Composable
fun AddExerciseToWorkoutDialog(
    exercise: Exercise,
    onDismiss: () -> Unit,
    onSave: (String, String, Exercise) -> Unit
) {
    var exerciseWeight by remember {
        mutableStateOf("")
    }
    var exerciseSetCount by remember {
        mutableStateOf("")
    }
    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .height(250.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF464646))
        ) {
            Column(Modifier.padding(horizontal = 15.dp)) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = exercise.category.joinToString { it.name },
                    fontFamily = quicksandBold,
                    fontSize = 12.sp,
                    color = customOrange
                )
                Text(
                    text = exercise.title,
                    fontFamily = quicksandBold,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .width(190.dp)
                        .height(40.dp)
                        .shadow(10.dp, RoundedCornerShape(10.dp))
                        .background(Color.White)
                ) {
                    CustomTextField(
                        value = exerciseWeight,
                        onValueChange = { exerciseWeight = it },
                        hintText = "Exercise weight",
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                width = 1.dp,
                                color = Color(0xFFFF9B70).copy(alpha = 0.2f),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(6.dp),
                        keyboardType = KeyboardType.Number,
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .width(190.dp)
                        .height(40.dp)
                        .shadow(10.dp, RoundedCornerShape(10.dp))
                        .background(Color.White)
                ) {
                    CustomTextField(
                        value = exerciseSetCount,
                        onValueChange = { exerciseSetCount = it },
                        hintText = "Exercise set count",
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                width = 1.dp,
                                color = Color(0xFFFF9B70).copy(alpha = 0.2f),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(6.dp),
                        keyboardType = KeyboardType.Number
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CustomButton(
                        onClick = {
                            onSave(exerciseWeight, exerciseSetCount, exercise)
                        },
                        color = Color.Magenta,
                        text = "Save"
                    )
                    CustomButton(
                        onClick = { onDismiss() }, color = Color.Magenta, text = "Cancel"
                    )
                }
            }
        }
    }
}

@Preview(group = "amogus")
@Composable
fun AddExerciseToWorkoutPrev() {
    AddExerciseToWorkoutDialog(MockExerciseData.mockExercises[1], {}, { _, _, _ -> })
}