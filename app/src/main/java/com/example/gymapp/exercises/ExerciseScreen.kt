package com.example.gymapp.exercises

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
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.gymapp.R
import com.example.gymapp.models.Exercise
import com.example.gymapp.ui.montserrati


@Composable
fun ExerciseScreen(viewModel: ExerciseDetailsViewModel, onExerciseClick: (Long) -> Unit) {
    val exercises by viewModel.exercises.observeAsState(listOf())

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(bottom = 16.dp + 56.dp)
        ) {
            Header(name = "Arturas")
            exercises.forEach { exercise ->
                Text(
                    text = exercise.title,
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
                        .clickable {
                            onExerciseClick(exercise.id)
                        }
                ) {
                    val painter = rememberImagePainter(exercise.imgUrl)
                    Image(
                        painter = painter,
                        contentDescription = "Exercise Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Text(
                    text = exercise.description,
                    fontFamily = montserrati,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 14.dp, start = 31.dp, end = 31.dp)
                )
            }
        }
    }
}


@Composable
fun ExerciseList(exercises: List<Exercise>, onIconClick: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
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
                        .clickable {
                            onIconClick()
                        }
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
fun ExercisesPreview() {
    val mockExercises = listOf(
        Exercise(
            0,
            "Description 1",
            200,
            "http",
            "haghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagaga"
        ),
        Exercise(1, "Description 2", 200, "http", "haghagaga"),
        Exercise(2, "Description 3", 200, "http", "haghagaga")
    )
    ExerciseList(exercises = mockExercises) {}
}

@Composable
fun Header(name: String) {
    val avatar = painterResource(R.drawable.avatar__1_)

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
                fontFamily = montserrati,
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
            )
        }

    }
    Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.padding(horizontal = 20.dp))
}
