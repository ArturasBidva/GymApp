package com.example.gymapp.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.gymapp.exercises.Header
import com.example.gymapp.models.Exercise
import com.example.gymapp.models.ExerciseWorkouts
import com.example.gymapp.models.Workout
import com.example.gymapp.ui.montserrati

@Composable
fun WorkoutsDetailsScreen(viewModel: WorkoutViewModel, onIconClick: (Long) -> Unit) {
    val workoutState by viewModel.workouts.observeAsState(null)
    val workout = workoutState
    if (workout != null) {
        WorkoutPreview(workout = workout) {
            onIconClick(it)
        }
    }
}

@Composable
fun WorkoutPreview(workout: List<Workout>, onIconClick: (Long) -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(bottom = 16.dp + 56.dp)
        ) {
            Header(name = "Arturas")
            workout.forEach { workout ->
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
                            color = Color.Gray,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .height(200.dp)
                        .fillMaxWidth()
                        .clickable { onIconClick(workout.id) }
                ) {
                    workout.exerciseWorkouts.forEach { exerciseWorkouts ->
                        exerciseWorkouts.exercise.imgUrl


                        val painter =
                            rememberImagePainter(exerciseWorkouts.exercise.imgUrl)
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
                workout.exerciseWorkouts.take(2).forEach { exerciseWorkout ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 30.dp)
                                .background(
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .height(73.dp)
                                .width(76.dp)
                        ) {
                            val painter = rememberImagePainter(exerciseWorkout.exercise.imgUrl)
                            Image(
                                painter = painter,
                                contentDescription = "Exercise Image",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(20.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Text(
                            text = exerciseWorkout.exercise.title,
                            fontFamily = montserrati,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.padding(bottom = 29.dp))
                }
            }
        }
    }
}


@Preview
@Composable
fun ExercisesDetailsPreview() {
    val mockExercise =
        Exercise(
            0,
            "Description 1",
            200,
            "http",
            "haghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagag",
            listOf()
        )
    val mockExerciseTwo =
        Exercise(
            0,
            "Description 2",
            200,
            "http",
            "haghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagag",
            listOf()
        )
    val mockExerciseWorkouts =
        ExerciseWorkouts(0, mockExercise, 0,400, 4)
    val mockExerciseWorkoutsTwo =
        ExerciseWorkouts(1, mockExerciseTwo, 0,500, 4)
    val mockWorkoutTwo =
        Workout(
            0,
            "kazkoks workoutas two",
            "To workoutoDescriptionas",
            listOf(mockExerciseWorkouts, mockExerciseWorkoutsTwo, mockExerciseWorkouts)
        )
    val mockWorkout =
        Workout(
            0,
            "kazkoks workoutas",
            "To workoutoDescriptionas",
            listOf(mockExerciseWorkouts, mockExerciseWorkoutsTwo, mockExerciseWorkouts)

        )
    WorkoutPreview(workout = listOf(mockWorkout, mockWorkoutTwo)) {

    }

}
