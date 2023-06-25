package com.example.gymapp.data.db.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ExerciseWithExerciseCategoryPair(
    @Embedded val exerciseEntity: ExerciseEntity,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "exerciseCategoryId",
        associateBy = Junction(ExerciseAndExerciseCategoryCrossRef::class)
    )
    val exerciseCategory: List<ExerciseCategoryEntity>
)