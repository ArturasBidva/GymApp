package com.example.gymapp.data.repositories.local.exercise

import com.example.gymapp.data.db.entities.ExerciseCategoryEntity
import com.example.gymapp.data.db.entities.ExerciseEntity
import com.example.gymapp.data.db.entities.ExerciseWithCategoryPair
import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.domain.exercises.ExerciseCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataExerciseService @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {


    fun getExercisesWithCategories(): Flow<List<Exercise>> {
        return exerciseRepository.getExercisesWithCategories()
            .map { it -> it.map { it.toExercise() } }
    }

    fun getExerciseCategories(): Flow<List<ExerciseCategory>> {
        return exerciseRepository.getExerciseCategories().map { it -> it.map { it.toCategory() } }
    }

    fun getExerciseById(exerciseId: Long): Exercise {
        return exerciseRepository.getExerciseById(exerciseId = exerciseId).toExercise()
    }



    private fun ExerciseWithCategoryPair.toExercise(): Exercise {
        return Exercise(
            id = this.exerciseEntity.exerciseId,
            title = this.exerciseEntity.title,
            imgUrl = this.exerciseEntity.imgUrl,
            description = this.exerciseEntity.description,
            categories = this.exerciseCategory.map { it.toCategory() }
        )
    }

    private fun ExerciseCategoryEntity.toCategory(): ExerciseCategory {
        return ExerciseCategory(
            id = this.exerciseCategoryId,
            category = this.category
        )
    }
}
