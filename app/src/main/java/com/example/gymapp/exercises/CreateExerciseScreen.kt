package com.example.gymapp.exercises

import MockRepository
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.R
import com.example.gymapp.models.Exercise
import com.example.gymapp.profile.CustomButton
import com.example.gymapp.ui.montserrati


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateExerciseScreen(
    viewModel: ExerciseDetailsViewModel,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var exerciseImage by remember { mutableStateOf("") }
    var exerciseDescription by remember { mutableStateOf("") }

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
                        text = "Create your Exercise",
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
                                        viewModel.createExercise(
                                            Exercise(
                                                title = title,
                                                weight = 0,
                                                imgUrl = exerciseImage,
                                                description = exerciseDescription
                                            )
                                        )
                                    }, modifier = Modifier.width(130.dp)
                                )
                                CustomButton(
                                    "Go Back",
                                    onClick = onNavigateBack,
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

@Preview
@Composable
fun CreateExerciseScreenPreview() {
    val repository = MockRepository()
    val viewModel =
        ExerciseDetailsViewModel(repository)
    CreateExerciseScreen(
        viewModel = viewModel,
        onNavigateBack = {}
    )
}