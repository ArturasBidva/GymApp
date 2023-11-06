package com.example.gymapp.ui.screens.createworkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.gymapp.R
import com.example.gymapp.ui.montserrati

@Composable
fun AddExerciseToWorkoutDialog(onDismiss: () -> Unit,onAddClick: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Content(onDismiss = onDismiss, onAddClick = {onAddClick()})
    }
}

@Composable
private fun Content(onDismiss: () -> Unit, onAddClick: () -> Unit) {
    Box(modifier = Modifier
        .padding(horizontal = 16.dp)
        .clip(RoundedCornerShape(8.dp))) {
        Image(
            painter = painterResource(id = R.drawable.createworkoutcardbg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .padding(40.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.createworkoutcompleted),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Workout created",
                fontFamily = montserrati,
                modifier = Modifier,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Do you want to add exercises to workout?",
                fontFamily = montserrati,
                modifier = Modifier,
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CustomButtonTwo(
                    onClick = {onAddClick()},
                    color = Color(0xFF562917),
                    text = "Yes",
                    width = 55,
                    height = 35
                )
                Spacer(modifier = Modifier.width(20.dp))
                CustomButtonTwo(
                    onClick = { onDismiss() },
                    color = Color(0xFF8A5037),
                    text = "No",
                    width = 55,
                    height = 35
                )
            }
        }
    }
}

@Preview
@Composable
fun CreateWorkoutDialogPrev() {
    Content(onDismiss = { /*TODO*/ }) {
        
    }
}