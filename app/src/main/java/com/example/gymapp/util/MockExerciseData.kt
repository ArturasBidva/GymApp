package com.example.gymapp.util

import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.domain.exercises.ExerciseCategory
import com.example.gymapp.domain.exercises.ExerciseState

object MockExerciseData {
    val mockExercises = listOf(
        Exercise(
            id = 0,
            title = "Exercise title",
            imgUrl = "hahaha",
            description = "Cia kazkoks aprasymas apie pratima",
            categories = listOf(ExerciseCategory(category = "Bicepsas"))
        ),
        Exercise(
            id = 1,
            title = "Exercise title",
            imgUrl = "http",
            description = "Cia kazkoks aprasymas apie pratima",
            categories = listOf(ExerciseCategory(category = "Tricepsas"))
        ),
        Exercise(
            id = 2,
            title = "Exercise title",
            imgUrl = "http",
            description = "Cia kazkoks aprasymas apie pratima",
            categories = listOf(ExerciseCategory(category = "Nugara"))
        )
    )
    val mockExerciseState = ExerciseState(
        exercise = Resource.Success(
            listOf(
                Exercise(
                    id = 1,
                    title = "Exercise 1",
                    description = "Exercise 1 description",
                    imgUrl = "https://example.com/exercise1.jpg",
                    categories = listOf(ExerciseCategory(1, "Category 1"))
                ),
                Exercise(
                    id = 2,
                    title = "Exercise 2",
                    description = "Exercise 2 description",
                    imgUrl = "https://example.com/exercise2.jpg",
                    categories = listOf(ExerciseCategory(2, "Category 2"))
                )
            )
        ),
        description = "Exercise description",
        imgUrl = "https://example.com/exercise.jpg",
        title = "Exercise title",
        category = listOf(ExerciseCategory(3, "Category"))
    )
}