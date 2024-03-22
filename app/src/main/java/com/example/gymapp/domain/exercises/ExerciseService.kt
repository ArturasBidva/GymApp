package com.example.gymapp.domain.exercises

import com.example.gymapp.data.db.entities.ExerciseAndExerciseCategoryCrossRef
import com.example.gymapp.data.db.entities.ExerciseCategoryEntity
import com.example.gymapp.data.db.entities.ExerciseEntity
import com.example.gymapp.data.db.entities.ExerciseWithCategoryPair
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
                exercise.categories.map { category ->
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

    private fun Exercise.toExerciseEntity() = ExerciseEntity(
        exerciseId = this.id,
        title = this.title,
        imgUrl = this.imgUrl,
        description = this.description
    )

    private fun ExerciseCategory.toEntity() = ExerciseCategoryEntity(
        exerciseCategoryId = this.id,
        category = this.category,
        isSelected = isSelected
    )
}