package com.example.gymapp.data.db.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class WorkoutWithExerciseWorkoutPair(
    @Embedded val workoutEntity: WorkoutEntity,
    @Relation(
        parentColumn = "workoutId",
        entityColumn = "exerciseWorkoutId",
        associateBy = Junction(WorkoutAndExerciseWorkoutCrossRef::class,
            parentColumn = "workoutId",
            entityColumn = "exerciseWorkoutId")
    )
    val exerciseWorkout: List<ExerciseWorkoutEntity>
)