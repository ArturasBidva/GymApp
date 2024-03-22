package com.example.gymapp.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["id", "exerciseWorkoutId"],
    foreignKeys = [ForeignKey(
        entity = WorkoutEntity::class,
        parentColumns = ["id"],
        childColumns = ["id"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = ExerciseWorkoutEntity::class,
        parentColumns = ["exerciseWorkoutId"],
        childColumns = ["exerciseWorkoutId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class WorkoutAndExerciseWorkoutCrossRef(
    val id: Long,
    val exerciseWorkoutId: Long
)