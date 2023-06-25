package com.example.gymapp.data.db.entities

import androidx.room.Entity

@Entity(primaryKeys = ["exerciseId", "exerciseCategoryId"])
data class ExerciseAndExerciseCategoryCrossRef(
    val exerciseId : Long,
    val exerciseCategoryId : Long
)