package com.example.gymapp.util

import com.example.gymapp.domain.exercises.ExerciseCategory

object MockExerciseCategoryData {
    val mockExerciseCategory = listOf(
        ExerciseCategory(0,"Legs",false),
        ExerciseCategory(1,"Chest",false),
        ExerciseCategory(2,"Full body",false),
        ExerciseCategory(3,"Arms",false),
        ExerciseCategory(4,"Back",false)
    )
}