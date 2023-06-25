package com.example.gymapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey
    val exerciseId: Long = 0,
    val title: String,
    val weight: Int = 0,
    val imgUrl: String,
    val description: String,
)