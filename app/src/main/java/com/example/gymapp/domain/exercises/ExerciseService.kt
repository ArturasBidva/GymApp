package com.example.gymapp.domain.exercises

import com.example.gymapp.data.db.entities.ExerciseAndExerciseCategoryCrossRef
import com.example.gymapp.data.db.entities.ExerciseCategoryEntity
import com.example.gymapp.data.db.entities.ExerciseEntity
import com.example.gymapp.data.db.entities.ExerciseWithExerciseCategoryPair
import com.example.gymapp.data.repositories.ExerciseRepository
import com.example.gymapp.data.repositories.MyRepository
import com.example.gymapp.util.Resource
import com.example.gymapp.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseService @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val repository: MyRepository
) {
    suspend fun deleteExercise(exercise: Exercise) {
        exerciseRepository.deleteExercise(exercise.toExerciseEntity())
    }
    suspend fun deleteExerciseById(exerciseId : Long) : Resource<Unit> {
       val response =  repository.deleteExerciseById(exerciseId)
        return if(response is Resource.Success){
            Resource.Success(Unit)
        }else Resource.Error(UiText.DynamicString("Error deleting exercise"))
    }

    private suspend fun insertExerciseAndExerciseCategoriesCrossRef(
        exerciseAndExerciseCategoryCrossRef: List<ExerciseAndExerciseCategoryCrossRef>
    ) {
        exerciseRepository.insertExerciseAndExerciseCategoryCrossRefs(
            exerciseAndExerciseCategoryCrossRef
        )
    }

    suspend fun getAllExerciseCategories(): Flow<Resource<List<ExerciseCategory>>>{
        return if(updateExercisesFromAPI()){
            exerciseRepository.getAllExerciseCategories().map {exerciseCategories -> exerciseCategories.map { it.toCategory() }  }
                .map { if (it.isEmpty()) Resource.Empty() else Resource.Success(it) }
        } else {
            flow { emit(Resource.Error(UiText.DynamicString("Error fetching exercise categories"))) }
        }
    }

    suspend fun getExercises(): Flow<Resource<List<Exercise>>> {
        return if (updateExercisesFromAPI()) {
            exerciseRepository.getAllExercises()
                .map { exercisesWithCategories -> exercisesWithCategories.map { it.toExercise() } }
                .map { if (it.isEmpty()) Resource.Empty() else Resource.Success(it) }
        } else {
            flow { emit(Resource.Error(UiText.DynamicString("Error fetching exercises"))) }
        }
    }



    private suspend fun updateExercisesFromAPI(): Boolean {
        val exercisesResponse = exerciseRepository.getExercisesFromApi()
        if (exercisesResponse.isSuccessful) {
            val exercises = exercisesResponse.body()!!
            exerciseRepository.deleteExercises()
            exerciseRepository.deleteCategories()
            exerciseRepository.insertExercises(exercises.map { it.toExerciseEntity() })
            val categoryEntities = exercises.map { it.category }.flatten().map { it.toEntity() }
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
        category = this.category,
        isSelected = isSelected
    )


    private fun ExerciseCategory.toEntity() = ExerciseCategoryEntity(
        exerciseCategoryId = this.id,
        category = this.category,
        isSelected = isSelected
    )
}