package com.example.gymapp.data.db.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ExerciseWithCategoryPair(
    @Embedded val exerciseEntity: ExerciseEntity,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "exerciseCategoryId",
        associateBy = Junction(ExerciseAndExerciseCategoryCrossRef::class,
            parentColumn = "exerciseId",
            entityColumn = "exerciseCategoryId")
    )
    val exerciseCategory: List<ExerciseCategoryEntity>
)