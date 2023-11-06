package com.example.gymapp.util

import com.example.gymapp.domain.workouts.Workout

object MockWorkoutData {
    val mockWorkouts = listOf(
        Workout(id = 0, title = "Legs day", description =  "This is random description", exerciseWorkouts = MockExerciseWorkoutData.mockExerciseWorkouts),
        Workout(id = 1, title = "Chest day", description =  "This is random description"),
        Workout(id = 3, title = "Back day", description =  "This is random description"),
        Workout(id = 4, title = "Arms day", description =  "This is random description")
    )
}