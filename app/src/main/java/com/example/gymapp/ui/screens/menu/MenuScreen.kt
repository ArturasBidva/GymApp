package com.example.gymapp.ui.screens.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.ui.screens.exercise.Header
import com.example.gymapp.ui.screens.profile.CustomButton
import com.example.gymapp.ui.CustomGray
import com.example.gymapp.ui.montserrati

@Composable
fun MenuScreen(
    onCreateExerciseClick: () -> Unit,
    onYourWorkoutsClick: () -> Unit,
    onCreateWorkoutClick: () -> Unit,
    addExerciseToWorkoutClick: () -> Unit,
    startWorkoutClick: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Header(name = "Arturas")
            Text(
                text = "Trainer's menu",
                fontSize = 24.sp,
                fontFamily = montserrati,
                modifier = Modifier.padding(top = 20.dp, start = 30.dp)
            )
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(20.dp)
                    )
                    .background(CustomGray)
                    .width(300.dp)
                    .height(420.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val buttonWidth = 224.dp
                    val buttonHeight = 47.dp
                    CustomButton(
                        text = "Create exercise",
                        onClick = { onCreateExerciseClick() },
                        modifier = Modifier
                            .width(buttonWidth)
                            .height(buttonHeight)
                    )
                    Spacer(modifier = Modifier.height(21.dp))
                    CustomButton(
                        text = "Your workouts",
                        onClick = { onYourWorkoutsClick() },
                        modifier = Modifier
                            .width(buttonWidth)
                            .height(buttonHeight)
                    )
                    Spacer(modifier = Modifier.height(21.dp))
                    CustomButton(
                        text = "Workout history",
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .width(buttonWidth)
                            .height(buttonHeight)
                    )
                    Spacer(modifier = Modifier.height(21.dp))
                    CustomButton(
                        text = "Create workout",
                        onClick = { onCreateWorkoutClick() },
                        modifier = Modifier
                            .width(buttonWidth)
                            .height(buttonHeight)
                    )
                    Spacer(modifier = Modifier.height(21.dp))
                    CustomButton(
                        text = "Add exercise to workout",
                        onClick = { addExerciseToWorkoutClick() },
                        modifier = Modifier
                            .width(buttonWidth)
                            .height(buttonHeight)
                    )
                    Spacer(modifier = Modifier.height(21.dp))
                    CustomButton(
                        text = "Start workout",
                        onClick = { startWorkoutClick() },
                        modifier = Modifier
                            .width(buttonWidth)
                            .height(buttonHeight)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun MenuScreenPreview() {
    MenuScreen({}, {}, {}, {},{})
}
