package com.example.gymapp.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["exerciseId", "exerciseCategoryId"],
    foreignKeys = [
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["exerciseId"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExerciseCategoryEntity::class,
            parentColumns = ["exerciseCategoryId"],
            childColumns = ["exerciseCategoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExerciseAndExerciseCategoryCrossRef(
    val exerciseId: Long,
    val exerciseCategoryId: Long
)