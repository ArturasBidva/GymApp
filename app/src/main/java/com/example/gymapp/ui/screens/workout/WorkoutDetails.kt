package com.example.gymapp.ui.screens.workout

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.gymapp.R
import com.example.gymapp.data.local.WorkoutLocal
import com.example.gymapp.domain.workouts.ExerciseWorkout
import com.example.gymapp.domain.workouts.Workout
import com.example.gymapp.ui.customGrayTwo
import com.example.gymapp.ui.customOrange
import com.example.gymapp.ui.montserrati
import com.example.gymapp.ui.quicksandBold
import com.example.gymapp.ui.quicksandMedium
import com.example.gymapp.util.MockWorkoutData
import com.example.gymapp.util.MockWorkoutLocalData

@Composable
fun WorkoutDetailsScreen(onDismiss: () -> Unit,workout: WorkoutLocal) {
    Dialog(
        onDismissRequest = {onDismiss()},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .width(300.dp)
                .height(564.dp),
            colors = CardDefaults.cardColors(containerColor = customGrayTwo)
        ) {
            Box(
                Modifier
                    .padding(top = 30.dp, bottom = 16.dp)
                    .padding(horizontal = 18.dp)
                    .width(263.dp)
                    .height(517.dp)
            ) {
                Column(Modifier.fillMaxWidth()) {
                    Text(
                        text = "Workout",
                        fontFamily = quicksandBold,
                        color = customOrange,
                        fontSize = 12.sp,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Full body",
                        fontFamily = quicksandBold,
                        color = Color.White,
                        fontSize = 20.sp,
                    )
                    Box(
                        Modifier
                            .width(206.dp)
                            .height(60.dp)
                    ) {
                        Text(
                            text = "Personalized exercise will help you gain strength",
                            fontFamily = quicksandMedium,
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                        Spacer(modifier = Modifier.width(30.dp))
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        workout.exerciseWorkouts.forEach { _ ->
                            items(workout.exerciseWorkouts) { exercise ->

                                ExerciseItem(exerciseWorkout = exercise)
                            }
                        }
                    }

                    Box(
                        Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Row() {
                            val removeWorkoutButton =
                                painterResource(R.drawable.removeworkoutbutton)
                            Image(
                                painter = removeWorkoutButton,
                                contentDescription = "Delete workout button"
                            )
                            Spacer(modifier = Modifier.width(25.dp))
                            val closeButton =
                                painterResource(R.drawable.close_button)
                            Image(
                                painter = closeButton,
                                modifier = Modifier.clickable { onDismiss() },
                                contentDescription = "Close button"
                            )
                        }

                    }

                }
            }
        }

    }

}

@Preview
@Composable
fun DialogPreview() {
    WorkoutDetailsScreen({}, MockWorkoutLocalData.mockWorkoutsLocal.first())
}

@Composable
fun ExerciseItem(exerciseWorkout: ExerciseWorkout) {
    Box(
        Modifier
            .width(300.dp)
            .height(87.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 7.dp)
        ) {
            Box(
                Modifier
                    .width(76.dp)
                    .height(73.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Blue)
            ) {

            }
            Spacer(modifier = Modifier.width(15.dp))
            Box(Modifier.fillMaxSize()) {
                Column() {
                    Text(
                        text = exerciseWorkout.exercise.title,
                        fontFamily = montserrati,
                        color = Color.White,
                        fontSize = 15.sp,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Weight: ${exerciseWorkout.weight}",
                        fontFamily = quicksandMedium,
                        color = Color.White,
                        fontSize = 15.sp,
                    )
                    Text(
                        text = "Set count: ${exerciseWorkout.goal}",
                        fontFamily = quicksandMedium,
                        color = Color.White,
                        fontSize = 15.sp,
                    )
                }
            }

        }
    }
    
}
