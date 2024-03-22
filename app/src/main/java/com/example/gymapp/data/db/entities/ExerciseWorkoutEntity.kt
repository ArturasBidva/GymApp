package com.example.gymapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exerciseWorkout")
class ExerciseWorkoutEntity(
    @PrimaryKey
    val exerciseWorkoutId: Long = 0,
    val completedCount: Int = 0,
    val weight: Int = 0,
    val goal: Int = 0,
    val exerciseId: Long
)