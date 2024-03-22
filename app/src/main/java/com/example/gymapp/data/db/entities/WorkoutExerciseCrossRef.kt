package com.example.gymapp.data.db.entities

import androidx.room.Entity

@Entity(primaryKeys = ["workoutId", "exerciseId"])
data class WorkoutExerciseCrossRef(
    val workoutId: Long,
    val exerciseId: Long
)