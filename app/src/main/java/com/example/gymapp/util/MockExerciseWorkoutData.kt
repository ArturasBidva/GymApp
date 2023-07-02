package com.example.gymapp.util

import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.domain.workouts.ExerciseWorkouts

object MockExerciseWorkoutData {
    val mockExerciseWorkouts = listOf(
        ExerciseWorkouts(
            id = 0,
            completedCount = 0,
            weight = 400,
            goal = 2,
            exercise = Exercise(
                id = 0,
                title = "Bicepsas",
                weight = 200,
                imgUrl = "Kastonas",
                description = "belekas",
                category = listOf()
            )
        ),
        ExerciseWorkouts(
            1,
            completedCount = 0,
            weight = 400,
            goal = 2,
            exercise = Exercise(
                1,
                title = "Tricepsas",
                weight = 400,
                imgUrl = "Kastonas",
                description = "belekas",
                category = listOf()
            )
        )
    )
}