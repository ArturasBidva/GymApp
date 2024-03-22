package com.example.gymapp.util

import androidx.compose.ui.graphics.Color
import com.example.gymapp.R
import com.example.gymapp.data.db.models.local.Schedule
import com.example.gymapp.data.db.models.local.WorkoutLocal
import com.example.gymapp.domain.workouts.Workout
import java.time.LocalDate
import java.time.LocalTime

object MockWorkoutData {
    val mockWorkouts = listOf(
        WorkoutLocal(id = 0, title = "Legs day", description =  "This is random description", exerciseWorkouts = MockExerciseWorkoutData.mockExerciseWorkouts),
        WorkoutLocal(id = 3, title = "Back day", description =  "This is random description"),
        WorkoutLocal(id = 4, title = "Arms day", description =  "This is random description")
    )
}
object MockWorkoutLocalData {
    val mockWorkoutsLocal = listOf(
        WorkoutLocal(
            id = 0,
            title = "Legs day",
            description = "This is random description"
        ),
        WorkoutLocal(
            id = 1,
            title = "Chest day",
            description = "This is random description",
            ),
        WorkoutLocal(
            id = 3,
            title = "Back day",
            description = "This is random description",
        ),
        WorkoutLocal(
            id = 4,
            title = "Arms day",
            description = "This is random description",
        )
    )
}