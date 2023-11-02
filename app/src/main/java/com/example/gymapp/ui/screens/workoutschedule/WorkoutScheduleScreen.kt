package com.example.gymapp.ui.screens.workoutschedule

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.R
import com.example.gymapp.ui.customOrange
import com.example.gymapp.ui.montserrati
import com.example.gymapp.ui.screens.mainscreen.Header
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutSchedule() {
    Surface(modifier = Modifier.fillMaxSize()) {
        var pickDate by remember {
            mutableStateOf(LocalDate.now())
        }
        var pickTime by remember {
            mutableStateOf(LocalTime.now())
        }

        val formattedDate by remember {
            derivedStateOf {
                DateTimeFormatter.ISO_LOCAL_DATE.format(pickDate)
            }
        }
        val formattedTime by remember {
            derivedStateOf {
                DateTimeFormatter.ofPattern("hh:mm").format(pickTime)
            }
        }
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
                                text = "Workout schedule",
                                color = Color.White,
                                fontFamily = montserrati,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = Instant.now().toEpochMilli()
                )
                DatePicker(state = datePickerState)
                var selectedDate = datePickerState.selectedDateMillis?.let {
                    Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC)

                }
                Text("Selected: ${selectedDate?.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: "no selection"}")
                Text("Selected: $formattedDate")

            }
        }

    }
}

@Preview
@Composable
fun WorkoutSchedulePreview() {
    WorkoutSchedule()
}