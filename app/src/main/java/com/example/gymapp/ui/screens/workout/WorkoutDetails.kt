package com.example.gymapp.ui.screens.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.gymapp.R
import com.example.gymapp.data.db.models.local.WorkoutLocal
import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.domain.workouts.ExerciseWorkout
import com.example.gymapp.domain.workouts.Workout
import com.example.gymapp.ui.montserrati
import com.example.gymapp.ui.quicksandMedium
import com.example.gymapp.ui.screens.mainscreen.TestHeader
import com.example.gymapp.util.MockExerciseWorkoutData
import com.example.gymapp.util.MockWorkoutData

@Composable
fun WorkoutDetailsScreen(workout: WorkoutLocal) {
    Content(workout = workout)

}

@Composable
private fun Content(workout: WorkoutLocal) {
    MaterialTheme {
        Surface {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                TestHeader(text = "Workout Details")
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .padding(horizontal = 44.dp)
                ) {
                    Column {
                        Text(text = workout.title, fontFamily = montserrati, fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .height(200.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.Blue)
                                .fillMaxSize()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.deadlift),
                                contentDescription = null,
                                modifier = Modifier.matchParentSize(),
                                contentScale = ContentScale.FillWidth
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        ExerciseBox(exerciseList = workout.exerciseWorkouts)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DialogPreview() {
    WorkoutDetailsScreen(workout = MockWorkoutData.mockWorkouts[0])
}

@Composable
fun ExerciseBox(exerciseList: List<ExerciseWorkout>) {
    exerciseList.forEach {
        Box {
            Row {
                Box(
                    Modifier
                        .height(76.dp)
                        .width(76.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Gray)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(it.exercise.imgUrl),
                        contentDescription = null,
                        modifier = Modifier.size(128.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column() {
                    Text(text = it.exercise.title, fontSize = 15.sp, fontFamily = montserrati)
                    Text(text = "Weight: " + it.weight, fontSize = 15.sp, fontFamily = quicksandMedium)
                    Text(text = "Set count: " + it.goal, fontSize = 15.sp, fontFamily = quicksandMedium)
                }

            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Preview
@Composable
fun ExerciseBoxPreview() {
    ExerciseBox(exerciseList = MockExerciseWorkoutData.mockExerciseWorkouts)

}