package com.example.gymapp.models

data class Exercise(
    val id: Long?,
    val title: String,
    val weight: Int,
    val imgUrl: String,
    val description: String
)