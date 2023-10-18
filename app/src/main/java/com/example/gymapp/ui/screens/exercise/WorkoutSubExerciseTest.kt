package com.example.gymapp.ui.screens.exercise

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.lerp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.R
import com.example.gymapp.ui.customGrayTwo
import com.example.gymapp.ui.customOrange
import com.example.gymapp.ui.quicksandBold
import com.example.gymapp.ui.quicksandMedium
import com.example.gymapp.ui.screens.workout.RemoveButtonIcon
import com.example.gymapp.ui.screens.workout.SeeMoreButton

@Composable
fun WorkoutSubExercise() {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val arrowRotation by animateFloatAsState(
        targetValue = if (isExpanded) 270f else 90f,
        label = ""
    )
    val topPadding by animateDpAsState(targetValue = if(isExpanded) 30.dp else 16.dp, label = "")
    val bottomPadding by animateDpAsState(targetValue = if(isExpanded) 16.dp else 8.dp, label = "")
    val spacingBetweenText by animateDpAsState(targetValue = if(isExpanded) 8.dp else 4.dp, label = "")
    val spacingBetweenControls by animateDpAsState(targetValue = if(isExpanded) 50.dp else 0.dp, label = "")
    val descriptionSize by animateTextStyleAsState(targetValue = if(isExpanded) {
        TextStyle(fontSize = 14.sp)
    } else {
        TextStyle(fontSize = 8.sp)
    })
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
            .clip(RoundedCornerShape(10.dp))
            .background(
                shape = RoundedCornerShape(10.dp),
                color = customGrayTwo
            )
            .wrapContentSize()
    )
    {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Spacer(modifier = Modifier.height(topPadding))
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
                    Spacer(modifier = Modifier.height(spacingBetweenText))
                    Text(
                        text = "Amogus",
                        fontSize = 16.sp,
                        fontFamily = quicksandBold,
                        color = Color.White,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                    Spacer(modifier = Modifier.height(spacingBetweenText))
                    Text(
                        text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500",
                        style = descriptionSize,
                        fontFamily = quicksandMedium,
                        color = Color.White,
                        lineHeight = 12.sp
                    )
                    Spacer(modifier = Modifier.height(bottomPadding))
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
            Spacer(modifier = Modifier.height(spacingBetweenControls))
            if(isExpanded) {
                Row {
                    RemoveButtonIcon({})
                    Spacer(modifier = Modifier.width(10.dp))
                   Text("Remove from workout",
                        color = Color(0xFFFF1500),
                        modifier = Modifier.clickable {  })
                    Spacer(modifier = Modifier.width(30.dp))
                    SeeMoreButton({})
                }
            }
        }
    }
}

@Preview
@Composable
fun WorkotuSubExercisePreview() {
    Column(modifier = Modifier.fillMaxSize()){
        WorkoutSubExercise()
    }
}

@Composable
fun animateTextStyleAsState(
    targetValue: TextStyle,
    animationSpec: AnimationSpec<Float> = spring(),
    finishedListener: ((TextStyle) -> Unit)? = null
): State<TextStyle> {

    val animation = remember { Animatable(0f) }
    var previousTextStyle by remember { mutableStateOf(targetValue) }
    var nextTextStyle by remember { mutableStateOf(targetValue) }

    val textStyleState = remember(animation.value) {
        derivedStateOf {
            lerp(previousTextStyle, nextTextStyle, animation.value)
        }
    }

    LaunchedEffect(targetValue, animationSpec) {
        previousTextStyle = textStyleState.value
        nextTextStyle = targetValue
        animation.snapTo(0f)
        animation.animateTo(1f, animationSpec)
        finishedListener?.invoke(textStyleState.value)
    }

    return textStyleState
}