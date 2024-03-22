package com.example.gymapp.domain.exercises

import com.example.gymapp.data.db.entities.ExerciseAndExerciseCategoryCrossRef
import com.example.gymapp.data.db.entities.ExerciseCategoryEntity
import com.example.gymapp.data.db.entities.ExerciseEntity
import com.example.gymapp.data.db.entities.ExerciseWithExerciseCategoryPair
import com.example.gymapp.data.repositories.MyRepository
import com.example.gymapp.data.repositories.local.exercise.ExerciseRepository
import com.example.gymapp.util.Resource
import com.example.gymapp.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseService @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val repository: MyRepository
) {

    suspend fun deleteExercise(exercise: Exercise): Resource<Unit> {
        return when (
            val response = repository.deleteExerciseById(exercise.id)
        ) {
            is Resource.Success -> {
                syncDataWithAPI()
                Resource.Success(Unit)
            }

            is Resource.Error -> {
                Resource.Error(UiText.DynamicString("Error deleting exercise"))
            }

            else -> response
        }
    }

    private suspend fun insertExerciseAndExerciseCategoriesCrossRef(
        exerciseAndExerciseCategoryCrossRef: List<ExerciseAndExerciseCategoryCrossRef>
    ) {
        exerciseRepository.insertExerciseAndExerciseCategoryCrossRefs(
            exerciseAndExerciseCategoryCrossRef
        )
    }

    fun getAllExerciseCategories(): Flow<List<ExerciseCategory>> {
        return exerciseRepository.getAllExerciseCategories()
            .map { exerciseCategories -> exerciseCategories.map { it.toCategory() } }
    }

    fun getExercises(): Flow<List<Exercise>> {
        return exerciseRepository.getAllExercises()
            .map { exercisesWithCategories ->
                exercisesWithCategories.map {
                    it.toExercise()
                }
            }
    }

    suspend fun syncDataWithAPI(): Boolean {
        val exercisesResponse = exerciseRepository.getExercisesFromApi()
        val categoryResponse = exerciseRepository.getCategoriesFromApi()
        if (exercisesResponse.isSuccessful && categoryResponse.isSuccessful) {
            val exercises = exercisesResponse.body()!!
            val categories = categoryResponse.body()!!
            exerciseRepository.deleteExercises()
            exerciseRepository.deleteCategories()
            exerciseRepository.insertExercises(exercises.map { it.toExerciseEntity() })
            val categoryEntities = categories.map { it }.map { it.toEntity() }
            exerciseRepository.insertExerciseCategories(categoryEntities)
            val refs = exercises.flatMap { exercise ->
                exercise.category.map { category ->
                    ExerciseAndExerciseCategoryCrossRef(
                        exerciseId = exercise.id,
                        exerciseCategoryId = category.id
                    )
                }
            }
            insertExerciseAndExerciseCategoriesCrossRef(refs)
            return true
        } else {
            return false
        }
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
        name = this.category,
        isSelected = isSelected
    )


    private fun ExerciseCategory.toEntity() = ExerciseCategoryEntity(
        exerciseCategoryId = this.id,
        category = this.name,
        isSelected = isSelected
    )
}