package com.example.gymapp.ui.screens.mainscreen

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gymapp.R
import com.example.gymapp.ui.customOrange
import com.example.gymapp.ui.customRedColor
import com.example.gymapp.ui.customSandColor
import com.example.gymapp.ui.customTextColor
import com.example.gymapp.ui.defaultTextColor
import com.example.gymapp.ui.fontColorForAvatarText
import com.example.gymapp.ui.montserrati
import com.example.gymapp.ui.quicksandBold
import com.example.gymapp.ui.reusable.LoadingCircle
import com.example.gymapp.ui.underLineColor

@Composable
fun MainScreen(
    mainScreenViewModel: MainViewModel = hiltViewModel(),
    navigateToWorkout: () -> Unit,
    navigateToExercise: () -> Unit,
    navigateToWorkoutSchedule: () -> Unit,
) {
    val uiState by mainScreenViewModel.uiState.collectAsState(MainScreenUiState())
    if (uiState.isLoading) {
        LoadingCircle()
    } else {
        Content(
            onWorkoutNavigateClick = navigateToWorkout,
            onExerciseNavigateClick = navigateToExercise,
            onWorkoutScheduleClick = navigateToWorkoutSchedule
        )
    }
}

@Composable
private fun Content(
    onWorkoutNavigateClick: () -> Unit,
    onExerciseNavigateClick: () -> Unit,
    onWorkoutScheduleClick: () -> Unit
) {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp + 56.dp)
            ) {
                TestHeader(text = "Home")
                Spacer(modifier = Modifier.padding(vertical = 23.dp))
                Text(
                    text = "Welcome back,",
                    fontFamily = montserrati,
                    fontSize = 24.sp,
                    color = defaultTextColor,
                    modifier = Modifier.padding(horizontal = 43.dp)
                )
                Column(modifier = Modifier.padding(horizontal = 43.dp)) {
                    Spacer(modifier = Modifier.padding(10.dp))
                    Box(
                        Modifier
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .height(90.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFF2F3FF)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = "This week you done 2 Workouts!", fontSize = 24.sp,
                                fontFamily = montserrati
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(12.dp))
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            Modifier
                                .shadow(
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .weight(1f)
                                .height(90.dp)
                                .clickable { onWorkoutNavigateClick() }
                                .clip(RoundedCornerShape(10.dp))
                                .background(customRedColor),
                            contentAlignment = Alignment.Center,
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                val avatar = painterResource(R.drawable.ion_fitness_sharp)
                                Image(painter = avatar, contentDescription = "Hearth")
                                Text(
                                    text = "Your workouts",
                                    fontFamily = montserrati,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }
                        }
                        Spacer(Modifier.width(10.dp))
                        Box(
                            modifier = Modifier
                                .shadow(
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .weight(1f)
                                .height(90.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(customSandColor),
                            contentAlignment = Alignment.Center,
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                val avatar = painterResource(R.drawable.vector)
                                Image(painter = avatar, contentDescription = "Hearth")
                                Text(
                                    text = "Workout history",
                                    fontFamily = montserrati,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .shadow(
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .weight(1f)
                                .fillMaxWidth()
                                .height(90.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFB5ADE9)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                val avatar = Icons.Outlined.Clear
                                Image(
                                    imageVector = avatar,
                                    contentDescription = "Hearth",
                                    Modifier.size(43.dp),
                                    colorFilter = ColorFilter.tint(Color(0xFF6D4040))
                                )
                                Text(
                                    text = "To be done",
                                    fontFamily = montserrati,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            modifier = Modifier
                                .shadow(
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .weight(1f)
                                .fillMaxWidth()
                                .height(90.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFDB9797))
                                .clickable { onExerciseNavigateClick() },
                            contentAlignment = Alignment.Center,
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                val avatar = painterResource(R.drawable.exercisegym)
                                Image(painter = avatar, contentDescription = "Hearth")
                                Text(
                                    text = "Exercises",
                                    fontFamily = montserrati,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        Modifier
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .height(90.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF91E183)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Row {
                                val avatar = painterResource(R.drawable.calendericon)
                                Icon(painter = avatar, contentDescription = "Calendar icon")
                                Spacer(modifier = Modifier.width(14.dp))
                                Box(
                                    Modifier
                                        .height(46.dp)
                                        .clickable { onWorkoutScheduleClick() }
                                ) {
                                    Text(
                                        text = "Make your workout schedule", fontSize = 16.sp,
                                        fontFamily = montserrati,
                                        color = Color.White
                                    )

                                }

                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(vertical = 12.dp))
                    Box(
                        Modifier
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .height(70.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFFF986B)),
                    ) {
                        Row(
                            Modifier
                                .fillMaxSize()
                                .padding(horizontal = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val avatar = painterResource(R.drawable.barbell)
                            Icon(painter = avatar, contentDescription = "Barbell")
                            Spacer(modifier = Modifier.width(10.dp))
                            Box(
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Column() {
                                    Text(
                                        text = "Ready for workout?", fontSize = 14.sp,
                                        fontFamily = montserrati,
                                        color = customTextColor
                                    )
                                    Text(
                                        text = "Click here to choose and start your daily workout!",
                                        fontSize = 10.sp,
                                        fontFamily = montserrati,
                                        color = customTextColor,
                                        lineHeight = 12.sp
                                    )

                                }
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun Header(name: String) {
    val avatar = painterResource(R.drawable.avatar__1_)
    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .padding(horizontal = 40.dp)
            .height(40.dp)
            .background(color = Color.White)
    )
    {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = avatar, contentDescription = "Avatar icon")
            Spacer(modifier = Modifier.padding(horizontal = 7.dp))
            Text(
                text = "Arturas",
                fontFamily = montserrati,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = fontColorForAvatarText
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = underLineColor.copy(alpha = 0.25f))

    )
}

@Preview
@Composable
fun MainScreenPrev() {
    Content(onWorkoutNavigateClick = { /*TODO*/ }, {
    }, {})
}

@Preview(group = "Amogus")
@Composable
fun TestHeaderPreview() {
    TestHeader(text = "Home")
}

@Composable
fun TestHeader(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(customOrange),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                if (text != "Home") {
                    Image(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
                Text(
                    text = text,
                    fontFamily = quicksandBold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false))
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = 5.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Arturas",
                        fontFamily = montserrati,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                    Spacer(modifier = Modifier.width(10.dp)) // Adjust spacer width as needed
                    Image(
                        painter = painterResource(R.drawable.avatar__1_),
                        contentDescription = "Avatar icon",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}