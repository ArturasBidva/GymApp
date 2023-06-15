package com.example.gymapp.workout

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.R
import com.example.gymapp.exercises.Header
import com.example.gymapp.models.Exercise
import com.example.gymapp.models.ExerciseWorkouts
import com.example.gymapp.models.Workout
import com.example.gymapp.ui.montserrati
import kotlinx.coroutines.delay
import java.lang.Math.PI

@Composable
fun OnGoingWorkout(workoutViewModel: WorkoutViewModel) {
    val workoutState by workoutViewModel.workout.observeAsState(null)
    val workout = workoutState
    if (workout != null) {
        OnGoingWorkoutScreen(workout = workout)
    }
}

@Composable
fun OnGoingWorkoutScreen(workout: Workout) {
    Surface(Modifier.fillMaxSize()) {
        val currentExerciseIndex = remember { mutableStateOf(0) }
        val ifWorkoutCompleted = remember { mutableStateOf(false) }
        val exerciseSetCount =
            remember {
                mutableStateOf(
                    workout.exerciseWorkouts
                            [currentExerciseIndex.value].completedCount
                )
            }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(bottom = 16.dp + 56.dp)
        ) {
            Header(name = "Arturas")
            Text(
                text = workout.title,
                fontFamily = montserrati,
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 24.dp, start = 18.dp)
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                color = Color.Gray,
                text = "Exercise",
                fontFamily = montserrati,
                fontSize = 18.sp,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            Text(
                text = workout.exerciseWorkouts[currentExerciseIndex.value].exercise.title,
                fontFamily = montserrati,
                fontSize = 32.sp,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                color = Color.Gray,
                text = "Weight and set count",
                fontFamily = montserrati,
                fontSize = 18.sp,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            Text(
                text = workout.exerciseWorkouts[currentExerciseIndex.value].exercise.weight.toString(),
                fontFamily = montserrati,
                fontSize = 32.sp,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(workout.exerciseWorkouts[currentExerciseIndex.value].goal) { index ->
                    if (index < exerciseSetCount.value) {
                        Box(
                            Modifier
                                .size(width = 47.dp, height = 15.dp)
                                .background(Color.Black)
                        )
                    } else {
                        Box(
                            Modifier
                                .size(width = 47.dp, height = 15.dp)
                                .background(Color.Gray)
                        )
                    }

                    if (index < workout.exerciseWorkouts[currentExerciseIndex.value].goal - 1) {
                        Spacer(modifier = Modifier.padding(horizontal = 12.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_right_1_svgrepo_com_2),
                    contentDescription = "",
                    modifier = Modifier
                        .graphicsLayer(
                            scaleX = -1f,
                            transformOrigin = TransformOrigin(0.5f, 0.5f)
                        )
                        .align(Alignment.CenterVertically)
                        .size(50.dp)
                )
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                Box(contentAlignment = Alignment.Center) {
                    Timer(
                        totalTime = 1L * 1400L,
                        handleColor = Color.DarkGray,
                        inactiveBarColor = Color.Gray,
                        activeBarColor = Color(0xFF37B900),
                        modifier = Modifier.size(200.dp),
                        onTimeFinish = {
                            if (ifWorkoutCompleted.value) {
                                ifWorkoutCompleted.value = false
                                exerciseSetCount.value = 0
                                currentExerciseIndex.value = 0
                                workout.exerciseWorkouts[currentExerciseIndex.value]
                            } else if (currentExerciseIndex.value + 1 == workout.exerciseWorkouts.size &&
                                exerciseSetCount.value + 1 >= workout.exerciseWorkouts[currentExerciseIndex.value].goal
                            ) {
                                exerciseSetCount.value++
                                ifWorkoutCompleted.value = true
                            } else if (exerciseSetCount.value + 1 >= workout.exerciseWorkouts[currentExerciseIndex.value].goal) {
                                currentExerciseIndex.value =
                                    (currentExerciseIndex.value + 1).coerceIn(
                                        0,
                                        workout.exerciseWorkouts.size - 1
                                    )
                                exerciseSetCount.value =
                                    workout.exerciseWorkouts[currentExerciseIndex.value].completedCount
                            } else {
                                exerciseSetCount.value++
                            }
                        }, ifWorkoutCompleted = ifWorkoutCompleted
                    )
                }
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                Image(
                    painter = painterResource(id = R.drawable.arrow_right_1_svgrepo_com_2),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(50.dp)
                        .clickable {
                            if (ifWorkoutCompleted.value) {
                                exerciseSetCount.value = 0
                                currentExerciseIndex.value = 0
                                workout.exerciseWorkouts[currentExerciseIndex.value]
                                ifWorkoutCompleted.value = false
                            } else if (exerciseSetCount.value >= workout.exerciseWorkouts[currentExerciseIndex.value].goal) {
                                currentExerciseIndex.value =
                                    (currentExerciseIndex.value + 1).coerceIn(
                                        0,
                                        workout.exerciseWorkouts.size - 1
                                    )
                                exerciseSetCount.value =
                                    workout.exerciseWorkouts[currentExerciseIndex.value].completedCount
                            } else {
                                exerciseSetCount.value++
                            }
                        }
                )
            }
            Spacer(modifier = Modifier.padding(top = 88.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 46.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                ) {
                    Column() {
                        Text(
                            color = Color.Black,
                            text = "Set time",
                            fontFamily = montserrati,
                            fontSize = 13.sp,
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        )
                        Text(
                            color = Color.Black,
                            text = "3:45",
                            fontFamily = montserrati,
                            fontSize = 13.sp,
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(80.dp)
                ) {
                    Column() {
                        Text(
                            color = Color.Black,
                            text = "Break time",
                            fontFamily = montserrati,
                            fontSize = 13.sp,
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        )
                        Text(
                            color = Color.Black,
                            text = "5:45",
                            fontFamily = montserrati,
                            fontSize = 13.sp,
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun OnGoingWorkoutScreenPreview() {
    val exerciseWorkout =
        ExerciseWorkouts(
            Exercise(
                "Bicepsas",
                200,
                "Kastonas",
                "belekas",
                listOf()
            ), 0,
            2
        )
    val exerciseWorkoutTwo =
        ExerciseWorkouts(
            Exercise(
                "Tricepsas",
                400,
                "Kastonas",
                "belekas",
                listOf()
            ), 0,
            2
        )
    OnGoingWorkoutScreen(
        Workout(
            0, "kazkas tokio",
            "gg",
            listOf(exerciseWorkout, exerciseWorkoutTwo)
        )
    )
}

@Composable
fun Timer(
    totalTime: Long,
    handleColor: Color,
    inactiveBarColor: Color,
    activeBarColor: Color,
    modifier: Modifier = Modifier,
    initialValue: Float = 0f,
    strokeWidth: Dp = 5.dp,
    onTimeFinish: () -> Unit,
    ifWorkoutCompleted: MutableState<Boolean>
) {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }

    var value by remember {
        mutableStateOf(initialValue)
    }
    var currentTime by remember {
        mutableStateOf(totalTime)
    }

    val totalBreakTime = 1000L


    var currentTimeForBreak by remember {
        mutableStateOf(totalBreakTime)
    }

    var isBreakTimeRunning by remember {
        mutableStateOf(false)
    }

    var isTimerRunning by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (currentTime > 0 && isTimerRunning && !isBreakTimeRunning) {
            delay(100L)
            currentTime -= 100L
            value = currentTime / totalTime.toFloat()
        } else if (currentTime <= 0 && isTimerRunning && !isBreakTimeRunning) {
            onTimeFinish()
            isTimerRunning = false
        }
    }
    LaunchedEffect(key1 = currentTimeForBreak, key2 = isBreakTimeRunning) {
        if (currentTimeForBreak > 0 && isBreakTimeRunning) {
            delay(100L)
            currentTimeForBreak -= 100L
            value = currentTimeForBreak / totalBreakTime.toFloat()
        } else if (currentTimeForBreak <= 0 && isBreakTimeRunning) {
            currentTimeForBreak = totalBreakTime
            isBreakTimeRunning = false
            currentTime = totalTime
        }
    }


    val timeInSeconds = currentTime / 1000
    val minutes = timeInSeconds / 60
    val seconds = timeInSeconds % 60
    val timeInSecondsForBreakTime = currentTimeForBreak / 1000
    val minutesForBreakTime = timeInSecondsForBreakTime / 60
    val secondsForBreakTime = timeInSecondsForBreakTime % 60
    val formattedTime = String.format("%d:%02d", minutes, seconds)
    val formattedTimeForBreak = String.format("%d:%02d", minutesForBreakTime, secondsForBreakTime)
    Box(contentAlignment = Alignment.Center,
        modifier = modifier.onSizeChanged {
            size = it
        }
    ) {
        Canvas(modifier = modifier) {
            drawArc(
                color = inactiveBarColor,
                startAngle = -215f,
                sweepAngle = 250f,
                useCenter = false,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = activeBarColor,
                startAngle = -215f,
                sweepAngle = 250f * value,
                useCenter = false,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            val center = Offset(size.width / 2f, size.height / 2f)
            val beta = (250f * value + 145f) * (PI / 180f).toFloat()
            val r = size.width / 2f
            val a = kotlin.math.cos(beta) * r
            val b = kotlin.math.sin(beta) * r
            drawPoints(
                listOf(Offset(center.x + a, center.y + b)),
                pointMode = PointMode.Points,
                color = handleColor,
                strokeWidth = (strokeWidth * 3f).toPx(),
                cap = StrokeCap.Round
            )
        }
        Box(
            modifier = Modifier
                .size(120.dp)
        ) {
            Column(
                Modifier.align(Alignment.Center)
            ) {
                if (ifWorkoutCompleted.value) {
                    Text(
                        color = Color.Black,
                        text = "Workout completed",
                        fontFamily = montserrati,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                } else if (currentTime > 0L) {
                    Text(
                        color = Color.Black,
                        text = "Set time",
                        fontFamily = montserrati,
                        fontSize = 20.sp,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                    Text(
                        color = Color.Black,
                        text = formattedTime,
                        fontFamily = montserrati,
                        fontSize = 35.sp,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                } else {
                    Text(
                        color = Color.Black,
                        text = "Break time",
                        fontFamily = montserrati,
                        fontSize = 20.sp,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                    Text(
                        color = Color.Black,
                        text = formattedTimeForBreak,
                        fontFamily = montserrati,
                        fontSize = 35.sp,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
            }
        }
        Button(
            onClick = {
                when {
                    currentTime > 0L -> {
                        isTimerRunning = !isTimerRunning
                    }

                    currentTime == 0L -> {
                        isBreakTimeRunning = !isBreakTimeRunning
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(150.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = when {
                    isTimerRunning || isBreakTimeRunning -> Color.Red
                    else -> Color.Gray
                }
            )
        ) {
            Text(
                text = when {
                    ifWorkoutCompleted.value -> "Restart workout"
                    isTimerRunning && currentTime > 0L -> "Pause"
                    !isTimerRunning && currentTime > 0L -> "Start"
                    isBreakTimeRunning && currentTimeForBreak > 0L -> "Pause Break"
                    !isBreakTimeRunning && currentTimeForBreak > 0L -> "Start Break"
                    else -> "Start"
                }
            )
        }
    }
}

@Preview
@Composable
fun TimerPreview() {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        val workoutCompleted = remember { mutableStateOf(true) }
        Box(contentAlignment = Alignment.Center) {
            Timer(
                totalTime = 100L * 1000L,
                handleColor = Color.DarkGray,
                inactiveBarColor = Color.Gray,
                activeBarColor = Color.DarkGray,
                modifier = Modifier.size(200.dp),
                onTimeFinish = {}, ifWorkoutCompleted = workoutCompleted
            )
        }
    }
}