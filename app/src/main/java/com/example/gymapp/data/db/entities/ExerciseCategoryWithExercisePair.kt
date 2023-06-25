package com.example.gymapp.data.db.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.gymapp.domain.exercises.ExerciseCategory

data class ExerciseCategoryWithExercisePair(
    @Embedded val exerciseCategory: ExerciseCategoryEntity,
    @Relation(
        parentColumn = "exerciseCategoryId",
        entityColumn = "exerciseId",
        associateBy = Junction(ExerciseAndExerciseCategoryCrossRef::class)
    )
    val exerciseEntity: List<ExerciseEntity>
)