package com.example.gymapp.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ExerciseWorkoutWithExercise(
    @Embedded val exerciseWorkout: ExerciseWorkoutEntity,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "exerciseWorkoutId"
    )
    val exercise: ExerciseEntity
)