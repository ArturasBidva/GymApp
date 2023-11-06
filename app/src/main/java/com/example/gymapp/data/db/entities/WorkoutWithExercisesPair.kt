package com.example.gymapp.data.db.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class WorkoutWithExercisesPair(
    @Embedded val workout: WorkoutEntity,
    @Relation(
        parentColumn = "workoutId",
        entityColumn = "exerciseId",
        associateBy = Junction(WorkoutExerciseCrossRef::class)
    )
    val exercises: List<ExerciseEntity>
)