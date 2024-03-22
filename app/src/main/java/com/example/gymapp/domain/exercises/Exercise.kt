package com.example.gymapp.domain.exercises

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Exercise(
    val id: Long = 0,
    val title: String,
    val weight: Int = 0,
    val imgUrl: String,
    val description: String,
    val category: List<ExerciseCategory>
) : Parcelable