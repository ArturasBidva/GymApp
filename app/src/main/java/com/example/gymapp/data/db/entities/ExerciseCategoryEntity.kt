package com.example.gymapp.data.db.entities

import androidx.room.Entity

@Entity
data class ExerciseCategoryEntity(
    val id: Long = 0L,
    val category: String,
    val isSelected: Boolean = false
)