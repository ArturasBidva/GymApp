package com.example.gymapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_categories")
data class ExerciseCategoryEntity(
    @PrimaryKey
    val exerciseCategoryId: Long,
    val category: String,
    val isSelected: Boolean
)