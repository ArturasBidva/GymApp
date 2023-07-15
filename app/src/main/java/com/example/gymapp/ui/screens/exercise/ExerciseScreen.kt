package com.example.gymapp.ui.screens.exercise

import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.gymapp.R
import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.domain.exercises.ExerciseState
import com.example.gymapp.ui.AppTheme
import com.example.gymapp.ui.CustomGray
import com.example.gymapp.ui.montserrati
import com.example.gymapp.util.MockExerciseData.mockExercises
import com.example.gymapp.util.Resource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen(
    viewModel: ExerciseViewModel,
    onExerciseClick: (Long) -> Unit,
    onAddExerciseButtonClick: () -> Unit,
    snackbarHostState: SnackbarHostState

) {
    val exercises by viewModel.uiState.collectAsState()
    val message by viewModel.eventFlow.collectAsState(null)
    Log.d("DebugTagg", "Message: $message")
    if (message != null) {
        val snackbarMessage = message!!.asString()
        Log.d("amogus", snackbarMessage)
        LaunchedEffect(key1 = message) {
            snackbarHostState.showSnackbar(message = snackbarMessage)
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.padding(bottom = 60.dp)
    ) {
        it
        ExerciseScreenComp(exercises = exercises,
            { onAddExerciseButtonClick() },
            { onExerciseClick(it) })
    }
}

@Composable
fun ExerciseScreenComp(
    exercises: ExerciseState,
    onAddExerciseButtonClick: () -> Unit,
    onExerciseClick: (Long) -> Unit,
) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Header(name = "Arturas")
                when (val resource = exercises.exercise) {
                    is Resource.Empty -> {
                        EmptyExerciseListBox(onButtonClick = { onAddExerciseButtonClick() })
                    }

                    is Resource.Error -> {
                        Text(
                            text = "Error: ${resource.message}",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    is Resource.Loading -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is Resource.Success -> {
                        resource.data?.let { exercise ->
                            ExerciseList(exercises = exercise) {
                                onExerciseClick(it)
                            }
                        }
                    }
                }
            }
        }
    }

@Preview
@Composable
fun ExerciseScreenPreview() {
    ExerciseScreenComp(exercises = ExerciseState(Resource.Loading()), {}, {})
}

@Composable
fun ExerciseList(exercises: List<Exercise>, onIconClick: (Long) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .align(Alignment.TopCenter)
        ) {
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
                            color = CustomGray,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .height(200.dp)
                        .fillMaxWidth()
                        .clickable {
                            onIconClick(exercise.id)
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
                Text(
                    text = exercise.category.joinToString { it.category },
                    fontFamily = montserrati,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(top = 6.dp, start = 30.dp)
                )
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Text(
                    text = exercise.description,
                    fontFamily = montserrati,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(start = 31.dp, end = 31.dp)
                )
            }
        }
    }
}



@Composable
fun EmptyExerciseListBox(onButtonClick: () -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.empty_exercise_list),
            color = Color.Black,
            fontFamily = montserrati,
            fontSize = 17.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
        )
        Button(onClick = { onButtonClick() }) {
            Text(
                text = "Create exercise",
                fontFamily = montserrati,
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}


@Preview
@Composable
fun EmptyExerciseListBoxPreview() {
    EmptyExerciseListBox({})
}

@Preview
@Composable
fun ExercisesPreview() {
    AppTheme() {
        ExerciseList(exercises = mockExercises) {}
    }
}

@Composable
fun Header(name: String) {
    val avatar = painterResource(R.drawable.avatar__1_)
    Box(
        modifier = Modifier
            .height(80.dp)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
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
    Divider(
        color = Color.Black,
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}
