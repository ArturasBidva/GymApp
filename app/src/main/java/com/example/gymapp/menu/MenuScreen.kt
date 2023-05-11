package com.example.gymapp.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gymapp.exercises.Header
import com.example.gymapp.profile.CustomButton
import com.example.gymapp.ui.montserrati

@Composable
fun MenuScreen(onCreateExerciseClick: () -> Unit) {
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
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 107.dp, end = 107.dp, bottom = 16.dp + 56.dp),
                contentAlignment = Alignment.Center
            ) {
                val boxWidth = 300.dp
                val boxHeight = 310.dp
                Box(
                    modifier = Modifier
                        .size(width = boxWidth, height = boxHeight)
                        .clip(
                            RoundedCornerShape(20.dp)
                        )
                        .background(Color.Gray)
                ) {
                    val buttonWidth = boxWidth * 0.45f
                    val buttonHeight = 42.dp
                    val buttonSpacing = (boxHeight - buttonHeight * 4) / 5

                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(buttonSpacing))
                        CustomButton(
                            text = "Create exercise",
                            onClick = { onCreateExerciseClick()},
                            modifier = Modifier.width(buttonWidth)
                        )
                        Spacer(modifier = Modifier.height(buttonSpacing))
                        CustomButton(
                            text = "Your workouts",
                            onClick = { /*TODO*/ },
                            modifier = Modifier.width(buttonWidth)
                        )
                        Spacer(modifier = Modifier.height(buttonSpacing))
                        CustomButton(
                            text = "Workout history",
                            onClick = { /*TODO*/ },
                            modifier = Modifier.width(buttonWidth)
                        )
                        Spacer(modifier = Modifier.height(buttonSpacing))
                        CustomButton(
                            text = "Change password",
                            onClick = { /*TODO*/ },
                            modifier = Modifier.width(buttonWidth)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MenuScreenPreview() {
    MenuScreen({})
}
