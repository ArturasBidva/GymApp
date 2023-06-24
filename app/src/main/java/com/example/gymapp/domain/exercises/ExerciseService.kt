package com.example.gymapp.domain.exercises

import com.example.gymapp.data.db.entities.ExerciseCategoryEntity
import com.example.gymapp.data.db.entities.ExerciseEntity
import com.example.gymapp.data.repositories.ExerciseRepository
//import com.example.gymapp.data.repositories.ExerciseRepository
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseService @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {

    suspend fun amogus() = exerciseRepository.amogus()

//    suspend fun insertExercise(exercise: Exercise) {
//        return exerciseRepository.insertExercise(exercise.toEntity())
//    }
//
//    suspend fun insertExercises(exercises: List<Exercise>) {
//        return exerciseRepository.insertExercises(exercises.map { it.toEntity() })
//    }
//
//    fun getAllExercises(): Flow<List<Exercise>> {
//        return exerciseRepository.getAllExercises()
//            .map { exerciseEntityList -> exerciseEntityList.map { it.toExercise() } }
//    }

    private fun ExerciseEntity.toExercise() = Exercise(
        id = this.id,
        title = this.title,
        weight = this.weight,
        imgUrl = this.imgUrl,
        description = this.description,
        category = this.categories.map { it.toCategory() }
    )

    private fun ExerciseCategoryEntity.toCategory() = ExerciseCategory(
        id = this.id,
        category = this.category,
        isSelected = isSelected
    )

    private fun Exercise.toEntity() = ExerciseEntity(
        id = this.id,
        title = this.title,
        weight = this.weight,
        imgUrl = this.imgUrl,
        description = this.description,
        categories = this.category.map { it.toEntity() }
    )

    private fun ExerciseCategory.toEntity() = ExerciseCategoryEntity(
        id = this.id,
        category = this.category,
        isSelected = isSelected
    )
}