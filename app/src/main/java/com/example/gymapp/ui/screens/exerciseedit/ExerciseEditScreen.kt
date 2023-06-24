package com.example.gymapp.ui.screens.exerciseedit

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.R
import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.ui.screens.exercise.ExerciseViewModel
import com.example.gymapp.ui.screens.profile.CustomButton
import com.example.gymapp.ui.montserrati

@Composable
fun ExerciseEditScreen(
    viewModel: ExerciseViewModel,
    exercise: Exercise,
    onGoBack: () -> Unit,
) {
    ExerciseEditScreenComp(
        exercise = exercise,
        onUpdateClick = { viewModel.updateExercise(exercise.id, it) }, onGoBack = onGoBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseEditScreenComp(
    exercise: Exercise,
    onUpdateClick: (Exercise) -> Unit,
    onGoBack: () -> Unit
) {
    var title by remember { mutableStateOf(TextFieldValue(exercise.title)) }
    var exerciseImage by remember { mutableStateOf(TextFieldValue(exercise.imgUrl)) }
    var exerciseDescription by remember { mutableStateOf(TextFieldValue(exercise.description)) }

    MaterialTheme(colorScheme = lightColorScheme()) {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.subtle),
                    contentDescription = "Background Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Edit your Exercise",
                        fontFamily = montserrati,
                        fontSize = 30.sp,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.Gray)
                            .width(308.dp)
                            .height(508.dp)
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = title,
                                onValueChange = { title = it },
                                label = { Text(text = "Title") },
                                placeholder = { Text(text = "Enter Title") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp)),
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.Gray,
                                    disabledTextColor = Color.Transparent,
                                    containerColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            TextField(
                                value = exerciseImage,
                                onValueChange = { exerciseImage = it },
                                label = { Text(text = "Image URL") },
                                placeholder = { Text(text = "Enter Image URL") },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .fillMaxWidth(),
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.Gray,
                                    disabledTextColor = Color.Transparent,
                                    containerColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            TextField(
                                value = exerciseDescription,
                                onValueChange = { exerciseDescription = it },
                                label = { Text(text = "Description") },
                                placeholder = { Text(text = "Enter Description") },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .fillMaxWidth()
                                    .height(150.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.Gray,
                                    disabledTextColor = Color.Transparent,
                                    containerColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                ), maxLines = 15
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                CustomButton(
                                    "Confirm",
                                    onClick = {
                                        val updatedExercise = Exercise(
                                            id = exercise.id,
                                            title = title.text,
                                            weight = 0,
                                            imgUrl = exerciseImage.text,
                                            description = exerciseDescription.text,
                                            listOf()
                                        )
                                        onUpdateClick(updatedExercise)
                                    }, modifier = Modifier.width(130.dp)
                                )
                                CustomButton(
                                    "Go Back",
                                    onClick = { onGoBack() },
                                    modifier = Modifier.width(130.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun ExerciseEditScreenPreview() {
    val exercise = Exercise(
        id = 1,
        title = "Bench Press",
        weight = 100,
        imgUrl = "https://example.com/image.png",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        listOf()
    )
    ExerciseEditScreenComp(exercise, {}, {}
    )
}