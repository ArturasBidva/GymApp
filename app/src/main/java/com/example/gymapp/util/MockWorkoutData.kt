package com.example.gymapp.util

import androidx.compose.ui.graphics.Color
import com.example.gymapp.R
import com.example.gymapp.data.local.Schedule
import com.example.gymapp.data.local.WorkoutLocal
import com.example.gymapp.domain.workouts.Workout
import java.time.LocalDate
import java.time.LocalTime

object MockWorkoutData {
    val mockWorkouts = listOf(
        Workout(id = 0, title = "Legs day", description =  "This is random description", exerciseWorkouts = MockExerciseWorkoutData.mockExerciseWorkouts),
        Workout(id = 3, title = "Back day", description =  "This is random description"),
        Workout(id = 4, title = "Arms day", description =  "This is random description")
    )
}
object MockWorkoutLocalData {
    val mockWorkoutsLocal = listOf(
        WorkoutLocal(
            id = 0,
            title = "Legs day",
            description = "This is random description",
            schedules = emptyList() // You can add schedules here if needed
        ),
        WorkoutLocal(
            id = 1,
            title = "Chest day",
            description = "This is random description",
            schedules = listOf(
                Schedule(
                    date = LocalDate.now(),
                    startTime = LocalTime.now().minusHours(2),
                    endTime = LocalTime.now().minusHours(1),
                    color = R.color.purple_700 // Assuming color is a property of Schedule
                )
            )
        ),
        WorkoutLocal(
            id = 3,
            title = "Back day",
            description = "This is random description",
            schedules = emptyList() // You can add schedules here if needed
        ),
        WorkoutLocal(
            id = 4,
            title = "Arms day",
            description = "This is random description",
            schedules = emptyList() // You can add schedules here if needed
        )
    )
}