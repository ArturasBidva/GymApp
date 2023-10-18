package com.example.gymapp.ui.screens.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.R
import com.example.gymapp.ui.anotherCustomSandColor
import com.example.gymapp.ui.customOrange
import com.example.gymapp.ui.customRedColor
import com.example.gymapp.ui.customSandColor
import com.example.gymapp.ui.customTextColor
import com.example.gymapp.ui.defaultTextColor
import com.example.gymapp.ui.fontColorForAvatarText
import com.example.gymapp.ui.montserrati
import com.example.gymapp.ui.thisWeekBoxColor
import com.example.gymapp.ui.underLineColor

@Composable
fun MainScreenComposable(onWorkoutNavigateClick: () -> Unit) {
    MaterialTheme() {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp + 56.dp)
            ) {
                Header(name = "Arturas")
                Spacer(modifier = Modifier.padding(vertical = 23.dp))
                Column(modifier = Modifier.padding(horizontal = 43.dp)) {
                    Text(
                        text = "Welcome back,",
                        fontFamily = montserrati,
                        fontSize = 24.sp,
                        color = defaultTextColor
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Box(
                        Modifier
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .width(274.dp)
                            .height(90.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(thisWeekBoxColor),
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
                    Spacer(modifier = Modifier.padding(23.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Box(
                            Modifier
                                .shadow(
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .width(132.dp)
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
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            Modifier
                                .shadow(
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .width(132.dp)
                                .height(90.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(customSandColor),
                            contentAlignment = Alignment.Center,
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                val avatar = painterResource(R.drawable.vector)
                                Image(painter = avatar, contentDescription = "Hearth")
                                Text(
                                    text = "Your workouts",
                                    fontFamily = montserrati,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(vertical = 26.dp))
                    Box(
                        Modifier
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .width(274.dp)
                            .height(90.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(thisWeekBoxColor),
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
                                        .width(156.dp)
                                        .height(46.dp)
                                ) {
                                    Text(
                                        text = "This week you done 2 Workouts!", fontSize = 16.sp,
                                        fontFamily = montserrati,
                                        color = Color.White
                                    )

                                }

                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(vertical = 50.dp))
                    Box(
                        Modifier
                            .width(274.dp)
                            .height(49.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(anotherCustomSandColor),
                    ) {
                        Row(
                            Modifier
                                .fillMaxSize()
                                .padding(horizontal = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val avatar = painterResource(R.drawable.barbell)
                            Icon(painter = avatar, contentDescription = "Barbell")
                            Spacer(modifier = Modifier.width(11.dp))
                            Box(
                                Modifier
                                    .width(136.dp)
                                    .height(45.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Column() {
                                    Text(
                                        text = "Ready for workout?", fontSize = 12.sp,
                                        fontFamily = montserrati,
                                        color = customTextColor
                                    )
                                    Text(
                                        text = "Click here to choose and start your daily workout!",
                                        fontSize = 7.sp,
                                        fontFamily = montserrati,
                                        color = customTextColor,
                                        lineHeight = 8.sp
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
fun MainScreenCompWithViewModel(navigateToWorkout: () -> Unit) {
    MainScreenComposable(onWorkoutNavigateClick = navigateToWorkout)
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
fun MainScreenCompPrev() {
    MainScreenComposable({})
}

@Preview(group = "Amogus")
@Composable
fun TestHeaderPreview() {
    TestHeader()
}

@Composable
fun TestHeader() {
    val avatar = painterResource(R.drawable.avatar__1_)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(customOrange)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_left_1_svgrepo_com_1),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Workouts",
                    fontFamily = montserrati,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .background(Color.White)
                        .width(1.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Arturas",
                    fontFamily = montserrati,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = fontColorForAvatarText
                )
                Spacer(modifier = Modifier.padding(horizontal = 7.dp))
                Image(painter = avatar, contentDescription = "Avatar icon")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}