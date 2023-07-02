package com.example.gymapp.util

import com.example.gymapp.domain.workouts.Workout

object MockWorkoutData {
    val mockWorkouts = listOf(
        Workout(id = 0, title = "Legs day", description =  "This is random description"),
        Workout(id = 1, title = "Arms day", description =  "This is random description")
    )
}