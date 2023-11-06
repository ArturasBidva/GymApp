package com.example.gymapp.ui.screens.exercise

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.R
import com.example.gymapp.domain.workouts.Workout
import com.example.gymapp.ui.customGrayTwo
import com.example.gymapp.ui.customOrange
import com.example.gymapp.ui.quicksandBold
import com.example.gymapp.ui.quicksandMedium

@Composable
fun WorkoutSelection(
    selectedWorkout: Workout,
    expanded: Boolean,
    onClick: () -> Unit
) {
    val arrowRotation by animateFloatAsState(
        targetValue = if (expanded) 270f else 90f,
        label = ""
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .clip(RoundedCornerShape(10.dp))
            .background(
                shape = RoundedCornerShape(10.dp),
                color = customGrayTwo
            )
            .wrapContentSize()
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Workout",
                        fontSize = 12.sp,
                        fontFamily = quicksandBold,
                        color = customOrange,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = selectedWorkout.title,
                        fontSize = 16.sp,
                        fontFamily = quicksandBold,
                        color = Color.White,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = selectedWorkout.description,
                        fontSize = 8.sp,
                        fontFamily = quicksandMedium,
                        color = Color.White,
                        lineHeight = 12.sp
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.backbuttonforexercisesscreen),
                    contentDescription = null,
                    modifier = Modifier
                        .graphicsLayer { rotationZ = arrowRotation },
                    contentScale = ContentScale.None
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview
@Composable
fun WorkoutSelectionPreview() {
    //WorkoutSelection(false,{})
}

@Composable
fun WorkoutSubSelection(title: String, description: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF4D4D4D)
            )
            .wrapContentSize()
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontSize = 12.sp,
                        fontFamily = quicksandBold,
                        color = Color.White,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = description,
                        fontSize = 8.sp,
                        fontFamily = quicksandMedium,
                        color = Color.White,
                        lineHeight = 12.sp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview
@Composable
fun WorkoutSubSelectionPreview() {
    WorkoutSubSelection("Amogus","Bandymas")
}