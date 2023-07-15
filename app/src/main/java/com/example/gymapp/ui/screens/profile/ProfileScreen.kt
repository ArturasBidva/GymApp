package com.example.gymapp.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.R
import com.example.gymapp.ui.customBlue
import com.example.gymapp.ui.montserrati
import com.example.gymapp.ui.screens.exercise.Header

@Composable
fun ProfileScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val avatar = painterResource(R.drawable.profile_circle_svgrepo_com_1)
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Header(name = "Arturas")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 107.dp, end = 107.dp, bottom = 16.dp + 56.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Your Profile",
                    fontSize = 24.sp,
                    fontFamily = montserrati,
                    modifier = Modifier.padding(top = 26.dp)
                )
                Image(
                    painter = avatar,
                    contentDescription = "User Avatar"
                )
                CustomButton(text = "Your trainers", onClick = { /*TODO*/ })
                Spacer(modifier = Modifier.padding(bottom = 17.dp))
                CustomButton(text = "Your workouts", onClick = { /*TODO*/ })
                Spacer(modifier = Modifier.padding(bottom = 17.dp))
                CustomButton(text = "Workout history", onClick = { /*TODO*/ })
                Spacer(modifier = Modifier.padding(bottom = 17.dp))
                CustomButton(text = "Change password", onClick = { /*TODO*/ })
                Spacer(modifier = Modifier.padding(bottom = 17.dp))
                CustomButton(text = "Report a bug", onClick = { /*TODO*/ })
            }
        }
    }
}


@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(195.dp)
            .height(42.dp),
        colors = ButtonDefaults.buttonColors(customBlue),
        contentPadding = PaddingValues()
    ) {
        Text(text = text, color = Color.Black, fontFamily = montserrati)
    }
}

@Composable
fun OneClickButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource =
        remember { MutableInteractionSource() }
) {
    var onClickHasExecuted by remember { mutableStateOf(false) }
    Button(
        onClick = {
            if(!onClickHasExecuted){
                onClickHasExecuted = true
                onClick()
            }
        },
        modifier = modifier
            .width(195.dp)
            .height(42.dp),
        colors = ButtonDefaults.buttonColors(customBlue),
        contentPadding = PaddingValues(),
        interactionSource = interactionSource
    ) {
        Text(text = text, color = Color.Black, fontFamily = montserrati)
    }
}

