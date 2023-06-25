package com.example.gymapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class ExerciseCategoryEntity(
    @PrimaryKey
    val exerciseCategoryId: Long = 0L,
    val category: String,
    val isSelected: Boolean = false
)