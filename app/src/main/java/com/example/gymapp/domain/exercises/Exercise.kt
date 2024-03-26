package com.example.gymapp.domain.exercises

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Exercise(
    val id: Long = 0,
    val title: String,
    val imgUrl: String,
    val description: String,
    val categories: List<ExerciseCategory>
) : Parcelable