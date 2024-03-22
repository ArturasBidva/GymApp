package com.example.gymapp.ui.screens.createworkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.R
import com.example.gymapp.domain.workouts.Workout
import com.example.gymapp.ui.customOrange
import com.example.gymapp.ui.montserrati
import com.example.gymapp.ui.quicksandBold
import com.example.gymapp.ui.reusable.LoadingCircle
import com.example.gymapp.ui.screens.mainscreen.Header
import com.example.gymapp.ui.screens.workout.WorkoutUiEvent
import com.example.gymapp.ui.screens.workout.WorkoutUiState
import com.example.gymapp.ui.screens.workout.WorkoutViewModel

@Composable
fun CreateWorkoutScreen(
    workoutViewModel: WorkoutViewModel,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit
) {
    val uiState by workoutViewModel.uiState.collectAsState()

    if (!uiState.isLoading) {
        Content(
            onUiEvent = workoutViewModel::onEvent,
            onBackClick = { onBackClick() },
            uiState = uiState,
            onAddClick = onAddClick
        )
    } else {
        LoadingCircle()
    }
}

@Composable
private fun Content(
    onUiEvent: (WorkoutUiEvent) -> Unit,
    onBackClick: () -> Unit,
    uiState: WorkoutUiState,
    onAddClick: () -> Unit
) {
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = imeState.value){
        if(imeState.value){
            scrollState.scrollTo(scrollState.maxValue)
        }
    }
    MaterialTheme {
        Surface(modifier = Modifier
            .fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.layered_waves_haikei),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(bottom = 16.dp + 56.dp)
            ) {

                Header(name = "Arturcikas")
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(customOrange)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(25.dp))
                            val avatar = painterResource(R.drawable.arrow)
                            Image(painter = avatar,
                                contentDescription = "Back arrow",
                                modifier = Modifier.clickable { onBackClick() })
                            Box(
                                modifier = Modifier.width(300.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Create workout",
                                    color = Color.White,
                                    fontFamily = montserrati,
                                    fontSize = 24.sp
                                )
                            }
                        }
                    }
                    CreateWorkoutBox(onUiEvent = onUiEvent,
                        onBackClick = onBackClick,
                        uiState = uiState,
                        onAddClick = { onAddClick() })

                }
            }
        }
    }
}

@Composable
fun CreateWorkoutBox(
    onUiEvent: (WorkoutUiEvent) -> Unit,
    onBackClick: () -> Unit,
    uiState: WorkoutUiState,
    onAddClick: () -> Unit
) {
    if (uiState.isCreated) {
        AddExerciseToWorkoutDialog(onDismiss = {
            onUiEvent(WorkoutUiEvent.DismissAddExerciseToWorkoutDialog)
        },
            onAddClick = {
                onUiEvent(WorkoutUiEvent.DismissAddExerciseToWorkoutDialog)
                onAddClick()
            })
    }

    var workoutTitle by remember {
        mutableStateOf("")
    }
    var workoutDescription by remember {
        mutableStateOf("")
    }

    Spacer(modifier = Modifier.height(20.dp))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(14.dp))
            Image(
                painter = painterResource(id = R.drawable.undraw_personal_trainer_re_cnua),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(60.dp))
            Box(
                modifier = Modifier
                    .width(190.dp)
                    .height(40.dp)
                    .shadow(10.dp, RoundedCornerShape(10.dp))
                    .background(Color.White)
            ) {
                CustomTextField(
                    value = workoutTitle,
                    onValueChange = { workoutTitle = it },
                    hintText = "Workout title",
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            width = 1.dp,
                            color = Color(0xFFFF9B70).copy(alpha = 0.2f),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(6.dp)
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Box(
                modifier = Modifier
                    .width(190.dp)
                    .height(40.dp)
                    .shadow(10.dp, RoundedCornerShape(5.dp))
                    .background(Color.White)
            ) {
                CustomTextField(
                    value = workoutDescription,
                    onValueChange = { workoutDescription = it },
                    hintText = "Workout description",
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            width = 1.dp,
                            color = Color(0xFFFF9B70).copy(alpha = 0.2f),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(6.dp)
                )
            }
            Spacer(modifier = Modifier.height(43.dp))
            Row(
                Modifier.padding(horizontal = 27.dp)
            ) {
                val workoutToSave = Workout(
                    null,
                    title = workoutTitle,
                    description = workoutDescription,
                    exerciseWorkouts = emptyList(),
                )
                CustomButton(
                    onClick = {
                        onUiEvent(WorkoutUiEvent.SaveWorkout(workoutToSave))
                    }, color = Color(0xFF562917), text = "Save"
                )
                Spacer(modifier = Modifier.width(14.dp))
                CustomButton(
                    onClick = { onBackClick() }, color = Color(0xFF8A5037), text = "Go back"
                )
            }

        }
    }
}

@Preview
@Composable
fun CreateWorkoutBoxPrev() {
    CreateWorkoutBox({}, {}, uiState = WorkoutUiState(), {})
}

@Preview
@Composable
fun ContentPrev() {
Content(onUiEvent = { }, onBackClick = {}, uiState = WorkoutUiState() ) {

}
}


@Composable
fun CustomButton(onClick: () -> Unit, color: Color, text: String) {
    Box(modifier = Modifier
        .shadow(1.dp, shape = RoundedCornerShape(45))
        .width(100.dp)
        .height(38.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(color = color)
        .clickable { onClick() }) {
        Text(
            text = text,
            fontFamily = quicksandBold,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun CustomButtonTwo(onClick: () -> Unit, color: Color, text: String, width: Int, height: Int) {
    Box(modifier = Modifier
        .shadow(1.dp, shape = RoundedCornerShape(45))
        .width(width.dp)
        .height(height.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(color = color)
        .clickable { onClick() }) {
        Text(
            text = text,
            fontFamily = quicksandBold,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(group = "amogus")
@Composable()
fun CustomButtonPrev() {
    CustomButton(onClick = { /*TODO*/ }, color = Color.Gray, text = "Save")
}

@Composable
fun WindowTemplate() {
    MaterialTheme() {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 16.dp + 56.dp)
            ) {
                Header(name = "Arturcikas")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(customOrange)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(25.dp))
                        val avatar = painterResource(R.drawable.arrow)
                        Image(painter = avatar,
                            contentDescription = "Back arrow",
                            modifier = Modifier.clickable { })
                        Box(
                            modifier = Modifier.width(300.dp), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Create workout",
                                color = Color.White,
                                fontFamily = montserrati,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Testas() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.layered_waves_haikei),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }
}

@Preview
@Composable
fun TestasPrev() {
    Testas()
}
