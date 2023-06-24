package com.example.gymapp.ui.screens.ongoingworkout

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
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.R
import com.example.gymapp.ui.screens.exercise.Header
import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.domain.workouts.ExerciseWorkouts
import com.example.gymapp.domain.workouts.Workout
import com.example.gymapp.ui.montserrati
import com.example.gymapp.workout.WorkoutViewModel
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
        var currentExerciseIndex by remember { mutableStateOf(0) }
        val currentExercise = workout.exerciseWorkouts[currentExerciseIndex]
        var isWorkoutCompleted by remember { mutableStateOf(false) }
        var exerciseSetCount by remember { mutableStateOf(currentExercise.completedCount) }

        val onRightClick = {
            if (isWorkoutCompleted) {
                exerciseSetCount = 0
                currentExerciseIndex = 0
                isWorkoutCompleted = false
            } else if (exerciseSetCount >= currentExercise.goal) {
                currentExerciseIndex =
                    (currentExerciseIndex + 1).coerceIn(
                        0,
                        workout.exerciseWorkouts.size - 1
                    )
                exerciseSetCount =
                    currentExercise.completedCount
            } else {
                exerciseSetCount++
            }
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(bottom = 72.dp)
        ) {
            Header(name = "Arturas")
            Text(
                text = workout.title,
                fontFamily = montserrati,
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 24.dp, start = 18.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SectionTitle(text = "Exercise")
                Spacer(modifier = Modifier.padding(top = 5.dp))
                Text(
                    text = currentExercise.exercise.title,
                    fontFamily = montserrati,
                    fontSize = 32.sp,
                )
                SectionTitle(text = "Weight and set count")
                Spacer(modifier = Modifier.padding(top = 5.dp))
                Text(
                    text = currentExercise.exercise.weight.toString(),
                    fontFamily = montserrati,
                    fontSize = 32.sp,
                )
                Spacer(modifier = Modifier.padding(top = 10.dp))
            }
            SetCountIndicatorRow(
                targetCount = currentExercise.goal,
                completedCount = exerciseSetCount
            )
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_right_1_svgrepo_com_2),
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(180f)
                        .align(Alignment.CenterVertically)
                        .size(50.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Box(contentAlignment = Alignment.Center) {
                    Timer(
                        totalTime = 1L * 1400L,
                        handleColor = Color.DarkGray,
                        inactiveBarColor = Color.Gray,
                        activeBarColor = Color(0xFF37B900),
                        modifier = Modifier.size(200.dp),
                        onTimeFinish = {
                            if (isWorkoutCompleted) {
                                isWorkoutCompleted = false
                                exerciseSetCount = 0
                                currentExerciseIndex = 0
                            } else if (currentExerciseIndex + 1 == workout.exerciseWorkouts.size &&
                                exerciseSetCount + 1 >= currentExercise.goal
                            ) {
                                exerciseSetCount++
                                isWorkoutCompleted = true
                            } else if (exerciseSetCount + 1 >= currentExercise.goal) {
                                currentExerciseIndex =
                                    (currentExerciseIndex + 1).coerceIn(
                                        0,
                                        workout.exerciseWorkouts.size - 1
                                    )
                                exerciseSetCount =
                                    currentExercise.completedCount
                            } else {
                                exerciseSetCount++
                            }
                        },
                        ifWorkoutCompleted = isWorkoutCompleted
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.arrow_right_1_svgrepo_com_2),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(50.dp)
                        .clickable { onRightClick() }
                )
            }
        }
    }
}

@Composable
fun SetCountIndicatorRow(targetCount: Int, completedCount: Int) {
    val setsLeft = targetCount - completedCount
    Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)){
        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(setsLeft) { SetCountIndicator(color = Color.Black) }
            repeat(completedCount) { SetCountIndicator(color = Color.Gray) }
        }
    }
}

@Composable
fun SetCountIndicator(color: Color) {
    Box(
        modifier = Modifier
            .size(width = 47.dp, height = 15.dp)
            .background(color = color)
    )
}

@Composable
fun SectionTitle(text: String, modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        color = Color.Gray,
        text = text,
        fontFamily = montserrati,
        fontSize = 18.sp,
        modifier = modifier
    )
}


@Preview
@Composable
fun OnGoingWorkoutScreenPreview() {
    val exerciseWorkout =
        ExerciseWorkouts(
            Exercise(
                id = 0,
                "Bicepsas",
                200,
                "Kastonas",
                "belekas",
                listOf()
            ), 0,
            400,
            2
        )
    val exerciseWorkoutTwo =
        ExerciseWorkouts(
            Exercise(
                1,
                "Tricepsas",
                400,
                "Kastonas",
                "belekas",
                listOf()
            ), 0,
            400,
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
    ifWorkoutCompleted: Boolean
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
        Box(modifier = Modifier.size(120.dp)) {
            Column(
                Modifier.align(Alignment.Center)
            ) {
                if (ifWorkoutCompleted) {
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
                    ifWorkoutCompleted -> "Restart workout"
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
        val workoutCompleted by remember { mutableStateOf(true) }
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