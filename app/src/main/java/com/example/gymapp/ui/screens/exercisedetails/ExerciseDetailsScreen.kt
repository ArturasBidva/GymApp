package com.example.gymapp.ui.screens.exercisedetails

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.domain.exercises.ExerciseEvent
import com.example.gymapp.ui.montserrati
import com.example.gymapp.ui.screens.exercise.ExerciseViewModel
import com.example.gymapp.ui.screens.exercise.Header
import com.example.gymapp.ui.screens.profile.CustomButton

@Composable
fun ExerciseDetailsScreen(
    exerciseViewModel: ExerciseViewModel,
    onUpdateExerciseClick: (Exercise) -> Unit,
    onDeleteExerciseClick: (ExerciseEvent) -> Unit
) {

    val exerciseState by exerciseViewModel.exercise.observeAsState(null)
    val exercise = exerciseState
    if (exercise != null) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(bottom = 16.dp + 56.dp)
            ) {
                Header(name = "Arturas")
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
                Divider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 20.dp, horizontal = 30.dp)
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomButton(
                        text = "Update exercise",
                        onClick = { onUpdateExerciseClick(exercise) },
                        modifier = Modifier
                            .width(195.dp)
                            .height(42.dp)
                    )
                    Spacer(modifier = Modifier.padding(top = 17.dp))
                    val onBackPressedDispatcher =
                        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
                    CustomButton(
                        text = "Delete exercise",
                        onClick = {
                            onDeleteExerciseClick(ExerciseEvent.DeleteExercise(exercise))
                            onBackPressedDispatcher?.onBackPressed()
                        },
                        modifier = Modifier
                            .width(195.dp)
                            .height(42.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun ExercisePreview(exercise: Exercise, onIconClick: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Header(name = "Arturas")
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
                        onIconClick()
                    }
            )
            {
            }
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(
                text = exercise.description,
                fontFamily = montserrati,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 14.dp, start = 31.dp, end = 31.dp)
            )
            Divider(
                color = Color.Black,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 30.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomButton(
                    text = "Update exercise",
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .width(195.dp)
                        .height(42.dp)
                )
                Spacer(modifier = Modifier.padding(top = 17.dp))
                CustomButton(
                    text = "Delete exercise",
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .width(195.dp)
                        .height(42.dp)
                )
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
            "haghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagagahaghagaga",
            listOf()
        )

    ExercisePreview(exercise = mockExercise) {}
}
