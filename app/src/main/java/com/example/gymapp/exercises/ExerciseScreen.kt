package com.example.gymapp.exercises

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.models.Exercise
import com.example.gymapp.ui.montserrati


@Composable
fun ExerciseScreen(viewModel: ExerciseViewModel) {
    val exercises by viewModel.exercises.observeAsState(listOf())
    Surface(modifier = Modifier.fillMaxSize(),color = Color.LightGray) {
        Column (modifier = Modifier.verticalScroll(rememberScrollState())){
            Header(name = "Arturas")
            exercises.forEach {
                Text(
                    text = it.title,
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
                ) {
                }
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Text(
                    text = it.description,
                    fontFamily = montserrati,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 14.dp, start = 31.dp, end = 31.dp)
                )
            }
        }
    }
}
@Composable
fun ExerciseList(exercises: List<Exercise>) {
    Surface(modifier = Modifier.fillMaxSize(),color = Color.LightGray) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Header(name = "Arturas")
            exercises.forEach {
                Text(
                    text = it.title,
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
                )
                {
                }
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Text(
                    text = it.description,
                    fontFamily = montserrati,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 14.dp, start = 31.dp, end = 31.dp)
                )
            }
        }
    }
}
@Preview
@Composable
fun exercisesPreview() {
    val mockExercises = listOf(
        Exercise(0, "Description 1",200,"hhtp","haghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagaga"),
        Exercise(1, "Description 2",200,"hhtp","haghagaga"),
        Exercise(2, "Description 3",200,"hhtp","haghagaga")
    )
    ExerciseList(exercises = mockExercises)
}

@Composable
fun Header(name: String) {
    val avatar = painterResource(com.example.gymapp.R.drawable.avatar__1_)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(73.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = name,
                fontFamily = montserrati, // Replace with Montserrat if you have it configured
                fontSize = 18.sp,
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterVertically)
                    .padding(end = 12.dp)
            )
            Image(
                painter = avatar,
                contentDescription = "User Avatar",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 18.dp)
                // Add any additional modifiers or styling as needed
            )
        }
    }
    Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.padding(horizontal = 20.dp))
}
