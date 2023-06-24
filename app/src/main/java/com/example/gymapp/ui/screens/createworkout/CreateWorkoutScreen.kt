package com.example.gymapp.ui.screens.createworkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gymapp.ui.screens.createexercise.CustomTextField
import com.example.gymapp.domain.workouts.Workout

@Composable
fun CreateWorkoutScreen(
    onNavigateBack: () -> Unit,
    onConfirmClick: (Workout) -> Unit
) {
    var workoutTitle by remember { mutableStateOf("") }
    var workoutDescription by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Create Workout",
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

                    Spacer(modifier = Modifier.padding(bottom = 17.dp))

                    CustomTextField(
                        input = workoutTitle, label = "Workout title"
                    ) { workoutTitle = it }
                    Spacer(modifier = Modifier.padding(bottom = 17.dp))
                    CustomTextField(
                        input = workoutDescription, label = "Workout description"
                    ) { workoutDescription = it }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = {
                            onConfirmClick(
                                Workout(
                                    0, workoutTitle, workoutDescription, listOf()
                                )
                            )
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
fun CreateWorkoutPreview() {
    CreateWorkoutScreen(onNavigateBack = { /*TODO*/ }, onConfirmClick = {})

}
