package com.example.gymapp.domain.exercises

import com.example.gymapp.util.Resource

data class ExerciseState(
    val exercise: Resource<List<Exercise>> = Resource.Loading(),
    val description: String = "",
    val imgUrl: String = "",
    val title: String = "",
    val category: List<ExerciseCategory> = emptyList(),
)
