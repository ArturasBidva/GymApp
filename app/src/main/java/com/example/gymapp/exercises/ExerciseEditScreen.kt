package com.example.gymapp.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.models.Exercise
import com.example.gymapp.ui.montserrati

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseEditScreen(
    exercise: Exercise,
    onSaveClick: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf(TextFieldValue(exercise.title)) }
    var exerciseImage by remember { mutableStateOf(TextFieldValue(exercise.imgUrl)) }
    var exerciseDescription by remember { mutableStateOf(TextFieldValue(exercise.description)) }

    Card(
        modifier = Modifier
            .width(300.dp)
            .height(600.dp)
            .background(Color.Blue)
    ) {
        Text(
            text = "Edit your Exercise",
            modifier = Modifier,
            fontFamily = montserrati,
            fontSize = 30.sp,
        )
        TextField(
            value = title,
            onValueChange = {
                title = it
            },
            label = { Text(text = "Title") },
            placeholder = { Text(text = "Enter Title") },
        )
        TextField(
            value = exerciseImage,
            onValueChange = {
                exerciseImage = it
            },
            label = { Text(text = "Image URL") },
            placeholder = { Text(text = "Enter Image URL") },
        )
        TextField(
            value = exerciseDescription,
            onValueChange = {
                exerciseDescription = it
            },
            label = { Text(text = "Description") },
            placeholder = { Text(text = "Enter Description") },
        )
        Button(onClick = onSaveClick) {
            Text(text = "Confirm")
        }
        Button(onClick = onNavigateBack) {
            Text(text = "Go Back")
        }
    }
}

