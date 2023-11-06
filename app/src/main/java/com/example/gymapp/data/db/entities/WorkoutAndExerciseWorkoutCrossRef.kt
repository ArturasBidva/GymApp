package com.example.gymapp.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["workoutId", "exerciseWorkoutId"],
    foreignKeys = [ForeignKey(
        entity = WorkoutEntity::class,
        parentColumns = ["workoutId"],
        childColumns = ["workoutId"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = ExerciseWorkoutEntity::class,
        parentColumns = ["exerciseWorkoutId"],
        childColumns = ["exerciseWorkoutId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class WorkoutAndExerciseWorkoutCrossRef(
    val workoutId: Long,
    val exerciseWorkoutId: Long
)