package com.example.gymapp.domain.exercises

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class ExerciseCategory(
    val id: Long = 0L,
    var name: String,
    var isSelected: Boolean = false
) : Parcelable