@file:JvmName("WorkoutDetailsScreenKt")

package com.example.gymapp.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.gymapp.R
import com.example.gymapp.exercises.CustomTextField
import com.example.gymapp.exercises.Header
import com.example.gymapp.models.Exercise
import com.example.gymapp.models.ExerciseCategory
import com.example.gymapp.models.ExerciseWorkouts
import com.example.gymapp.models.Workout
import com.example.gymapp.ui.montserrati

@Composable
fun WorkoutDetailScreen(viewModel: WorkoutViewModel) {
    val workoutState by viewModel.workout.observeAsState(null)
    val workout = workoutState
    if (workout != null) {
        WorkoutDetailPreview(workout = workout, updateExerciseWorkout = { updatedExerciseWorkout ->
            viewModel.updateExerciseWorkout(updatedExerciseWorkout, workout.id)
        })
    }
}

@Composable
fun WorkoutDetailPreview(workout: Workout, updateExerciseWorkout: (ExerciseWorkouts) -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(bottom = 16.dp + 56.dp)
        ) {
            Header(name = "Arturas")
            Text(
                text = workout.title,
                fontFamily = montserrati,
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 20.dp, start = 30.dp)
            )
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Box(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .background(
                        color = Color.Gray, shape = RoundedCornerShape(20.dp)
                    )
                    .height(200.dp)
                    .fillMaxWidth()
            ) {
                workout.exerciseWorkouts.forEach { exerciseWorkouts ->
                    exerciseWorkouts.exercise.imgUrl


                    val painter = rememberImagePainter(exerciseWorkouts.exercise.imgUrl)
                    Image(
                        painter = painter,
                        contentDescription = "Exercise Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(
                text = workout.description,
                fontFamily = montserrati,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 14.dp, start = 31.dp, end = 31.dp)
            )
            Spacer(modifier = Modifier.padding(top = 22.dp))
            workout.exerciseWorkouts.forEach { exerciseWorkout ->
                ExerciseBox(
                    exerciseWorkouts = exerciseWorkout,
                    updateExerciseWorkouts = updateExerciseWorkout
                )
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 29.dp))
    }
}


@Preview
@Composable
fun WorkoutDetailPreviews() {
    val mockExercise = Exercise(
        0,
        "Mirties trauka",
        200,
        "http",
        stringResource(id = R.string.mock_description),
        listOf(ExerciseCategory("Kojos"), ExerciseCategory("Rankos"))
    )
    val mockExerciseTwo = Exercise(
        0, "Prisitraukimai", 200, "http", stringResource(id = R.string.mock_description), listOf()
    )
    val mockExerciseWorkouts = ExerciseWorkouts(0, mockExercise, 0, 500, 4)
    val mockExerciseWorkoutsTwo = ExerciseWorkouts(1, mockExerciseTwo, 0, 400, 4)
    val mockWorkout = Workout(
        0,
        "kazkoks workoutas",
        "To workoutoDescriptionas",
        listOf(mockExerciseWorkouts, mockExerciseWorkoutsTwo, mockExerciseWorkouts)

    )
    WorkoutDetailPreview(workout = mockWorkout, {})

}

@Composable
fun ExerciseBox(
    exerciseWorkouts: ExerciseWorkouts, updateExerciseWorkouts: (ExerciseWorkouts) -> Unit
) {
    var openEditScreen by remember { mutableStateOf(false) }
    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                openEditScreen = true
            }) {
        Box(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .background(
                    color = Color.Gray, shape = RoundedCornerShape(20.dp)
                )
                .height(73.dp)
                .width(76.dp)
        ) {
            val painter = rememberImagePainter(exerciseWorkouts.exercise.imgUrl)
            Image(
                painter = painter,
                contentDescription = "Exercise Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop
            )
        }
        if (!openEditScreen) {
            Column() {
                Text(
                    text = exerciseWorkouts.exercise.title,
                    fontFamily = montserrati,
                    fontSize = 13.sp,
                )
                Text(
                    text = "Exercise sets count: " + exerciseWorkouts.goal,
                    fontFamily = montserrati,
                    fontSize = 10.sp,
                )
                Text(
                    text = "Weight: " + exerciseWorkouts.weight,
                    fontFamily = montserrati,
                    fontSize = 10.sp,
                )
                Text(
                    text = "Category: " + exerciseWorkouts.exercise.category.joinToString { c -> c.category },
                    fontFamily = montserrati,
                    fontSize = 10.sp,
                )
                Spacer(Modifier.height(20.dp))
            }
            Box(
                Modifier
                    .size(20.dp)
                    .background(Color.Blue)
                    .clickable {
                        openEditScreen = true
                    }) {

            }
            Spacer(modifier = Modifier.width(20.dp))
            Box(
                Modifier
                    .size(20.dp)
                    .background(Color.Green)
            ) {

            }
        } else {
            ExerciseEditBox(exerciseWorkouts = exerciseWorkouts,
                onConfirmClick = updateExerciseWorkouts,
                onBackClick = { openEditScreen = false })
        }
    }
}

@Preview
@Composable
fun ExerciseBoxPreview() {
    val mockExercise = Exercise(
        0, "Mirties trauka", 200, "http", stringResource(id = R.string.mock_description), listOf()
    )
    val mockExerciseWorkouts = ExerciseWorkouts(0, mockExercise, 0, 400, 4)
    ExerciseBox(mockExerciseWorkouts, {})
}

@Composable
fun ExerciseEditBox(
    exerciseWorkouts: ExerciseWorkouts,
    onConfirmClick: (ExerciseWorkouts) -> Unit,
    onBackClick: (Boolean) -> Unit
) {
    var exerciseWorkoutGoal by remember { mutableStateOf(exerciseWorkouts.goal) }
    var exerciseWorkoutWeight by remember { mutableStateOf(exerciseWorkouts.weight) }
    Row(Modifier.fillMaxWidth().padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.spacedBy(30.dp)) {
        Column() {
            Text(
                text = exerciseWorkouts.exercise.title,
                fontFamily = montserrati,
                fontSize = 13.sp,
            )
            CustomTextField(
                input = exerciseWorkoutGoal, label = "Set count", onValueChange = {
                    exerciseWorkoutGoal = it
                }, size = 80.dp
            )
            CustomTextField(
                input = exerciseWorkoutWeight, label = "Weight", onValueChange = {
                    exerciseWorkoutWeight = it
                }, size = 80.dp
            )
            Text(
                text = "Category: " + exerciseWorkouts.exercise.category.joinToString { c -> c.category },
                fontFamily = montserrati,
                fontSize = 10.sp,
            )
            Spacer(Modifier.height(20.dp))
        }
        Box(
            Modifier
                .size(20.dp)
                .background(Color.Yellow)
                .clickable {
                    val exerciseWorkout = ExerciseWorkouts(
                        exerciseWorkouts.id,
                        exerciseWorkouts.exercise,
                        0,
                        exerciseWorkoutWeight,
                        exerciseWorkoutGoal
                    )
                    onConfirmClick(exerciseWorkout)
                }) {

        }
        Box(
            Modifier
                .size(20.dp)
                .background(Color.Red)
                .clickable {
                    onBackClick(false)
                }) {

        }
    }
}

@Preview
@Composable
fun ExerciseEditBoxPreview() {
    val mockExercise = Exercise(
        0, "Mirties trauka", 200, "http", stringResource(id = R.string.mock_description), listOf()
    )
    val mockExerciseWorkouts = ExerciseWorkouts(0, mockExercise, 0, 400, 4)
    ExerciseEditBox(exerciseWorkouts = mockExerciseWorkouts, onConfirmClick = {}, onBackClick = {})
}