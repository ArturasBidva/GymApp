package com.example.gymapp.ui.screens.createworkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.gymapp.R
import com.example.gymapp.ui.montserrati
import com.example.gymapp.ui.quicksandMedium

@Composable
fun CreateWorkoutDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Card(
            modifier = Modifier
                .width(225.dp)
                .height(250.dp)
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.createworkoutcardbg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Column(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.createworkoutcompleted),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(13.dp))
                    Text(
                        text = "Workout created, do you want to add exercises to workout?",
                        fontFamily = montserrati,
                        modifier = Modifier,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Click yes to choose exercise",
                        fontFamily = quicksandMedium,
                        modifier = Modifier,
                        fontSize = 6.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CustomButtonTwo(
                            onClick = { /*TODO*/ },
                            color = Color(0xFF562917),
                            text = "Yes",
                            width = 55,
                            height = 35
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        CustomButtonTwo(
                            onClick = {onDismiss() },
                            color = Color(0xFF8A5037),
                            text = "No",
                            width = 55,
                            height = 35
                        )

                    }
                }


            }
        }
    }
}

@Preview
@Composable
fun CreateWorkoutDialogPrev() {
    CreateWorkoutDialog({})
}