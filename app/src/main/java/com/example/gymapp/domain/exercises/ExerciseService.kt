package com.example.gymapp.domain.exercises

import com.example.gymapp.data.db.entities.ExerciseAndExerciseCategoryCrossRef
import com.example.gymapp.data.db.entities.ExerciseCategoryEntity
import com.example.gymapp.data.db.entities.ExerciseEntity
import com.example.gymapp.data.db.entities.ExerciseWithExerciseCategoryPair
import com.example.gymapp.data.repositories.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseService @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {

    suspend fun insertExercise(exercise: Exercise) {
        exerciseRepository.insertExercise(exercise.toExerciseEntity())
    }

    suspend fun insertExercises(exercises: List<Exercise>) {
        exerciseRepository.insertExercises(exercises.map { it.toExerciseEntity() })
    }

    suspend fun insertExerciseCategory(exerciseCategory: ExerciseCategory) {
        exerciseRepository.insertExerciseCategory(exerciseCategory.toEntity())
    }

    suspend fun insertExerciseCategories(exerciseCategory: List<ExerciseCategory>) {
        exerciseRepository.insertExerciseCategories(exerciseCategory.map { it.toEntity() })
    }

    suspend fun insertExerciseAndExerciseCategoriesCrossRef(exerciseAndExerciseCategoryCrossRef: List<ExerciseAndExerciseCategoryCrossRef>) {
        exerciseRepository.insertExerciseAndExerciseCategoryCrossRefs(exerciseAndExerciseCategoryCrossRef)
    }

    fun getAllExercises(): Flow<List<Exercise>> {
        return exerciseRepository.getAllExercises()
            .map { exerciseWithExerciseCategory -> exerciseWithExerciseCategory.map { it.toExercise() } }
    }

    private fun ExerciseWithExerciseCategoryPair.toExercise() = Exercise(
        id = this.exerciseEntity.exerciseId,
        title = this.exerciseEntity.title,
        weight = this.exerciseEntity.weight,
        imgUrl = this.exerciseEntity.imgUrl,
        description = this.exerciseEntity.description,
        category = this.exerciseCategory.map { it.toCategory() }
    )

    private fun Exercise.toExerciseEntity() = ExerciseEntity(
        exerciseId = this.id,
        title = this.title,
        weight = this.weight,
        imgUrl = this.imgUrl,
        description = this.description
    )


    private fun ExerciseCategoryEntity.toCategory() = ExerciseCategory(
        id = this.exerciseCategoryId,
        category = this.category,
        isSelected = isSelected
    )


    private fun ExerciseCategory.toEntity() = ExerciseCategoryEntity(
        exerciseCategoryId = this.id,
        category = this.category,
        isSelected = isSelected
    )
}