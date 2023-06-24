package com.example.gymapp.domain.exercises

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class ExerciseCategory(
  val id: Long = 0L,
  val category: String,
  val isSelected: Boolean = false
) : Parcelable