package com.example.gymapp.domain.exercises

import android.util.Log
import com.example.gymapp.data.db.entities.ExerciseAndExerciseCategoryCrossRef
import com.example.gymapp.data.db.entities.ExerciseCategoryEntity
import com.example.gymapp.data.db.entities.ExerciseEntity
import com.example.gymapp.data.db.entities.ExerciseWithExerciseCategoryPair
import com.example.gymapp.data.repositories.ExerciseRepository
import com.example.gymapp.util.Resource
import com.example.gymapp.util.UiText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
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

    suspend fun deleteExercises(exercises: List<Exercise>) {
        exerciseRepository.deleteExercises(exercises.map { it.toExerciseEntity() })
    }

    suspend fun deleteExercise(exercise: Exercise) {
        exerciseRepository.deleteExercise(exercise.toExerciseEntity())
    }

    suspend fun insertExerciseCategory(exerciseCategory: ExerciseCategory) {
        exerciseRepository.insertExerciseCategory(exerciseCategory.toEntity())
    }

    suspend fun insertExerciseCategories(exerciseCategory: List<ExerciseCategory>) {
        exerciseRepository.insertExerciseCategories(exerciseCategory.map { it.toEntity() })
    }

    suspend fun insertExerciseAndExerciseCategoriesCrossRef(
        exerciseAndExerciseCategoryCrossRef: List<ExerciseAndExerciseCategoryCrossRef>
    ) {
        exerciseRepository.insertExerciseAndExerciseCategoryCrossRefs(
            exerciseAndExerciseCategoryCrossRef
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getAllExercises(): Flow<Resource<List<Exercise>>> {

        val exerciseFlow: Flow<Resource<List<Exercise>>> = flow {
            val exercisesResponse = exerciseRepository.getExercisesFromApi()
            if (exercisesResponse.isSuccessful) {
                Log.e("Amogus", "Success")
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
                //insertExerciseAndExerciseCategoriesCrossRef(refs)
                emit(Resource.Error(UiText.DynamicString(exercisesResponse.message())))
            } else {
                Log.e("Amogus", "Erroras")
                emit(Resource.Error(UiText.DynamicString(exercisesResponse.message())))
            }
        }
//        }.flatMapLatest { exerciseResource ->
//            Log.e("Amogus","Success23")
//            if (exerciseResource !is Resource.Error<List<Exercise>>) {
//                exerciseRepository.getAllExercises()
//                    .map { exercisesWithCategories -> exercisesWithCategories.map { it.toExercise() } }
//                    .map { if (it.isEmpty()) Resource.Empty() else Resource.Success(it) }
//            } else flow {
//                emit(Resource.Error(exerciseResource.message!!))
//            }
//        }
        return exerciseFlow.flatMapLatest { exerciseResource ->
            Log.e("Amogus", "Success23")
            if (exerciseResource !is Resource.Error<List<Exercise>>) {
                exerciseRepository.getAllExercises()
                    .map { exercisesWithCategories -> exercisesWithCategories.map { it.toExercise() } }
                    .map { if (it.isEmpty()) Resource.Empty() else Resource.Success(it) }
            } else flow {
                emit(Resource.Error(exerciseResource.message!!))
            }
        }

//        val exercisesResponse = exerciseRepository.getExercisesFromApi()
//        if (exercisesResponse.isSuccessful) {
//            val exercises = exercisesResponse.body()!!
//            exerciseRepository.deleteExercises()
//            exerciseRepository.deleteCategories()
//            exerciseRepository.insertExercises(exercises.map { it.toExerciseEntity() })
//            val categoryEntities = exercises.map { it.category }.flatten().map { it.toEntity() }
//            exerciseRepository.insertExerciseCategories(categoryEntities)
//            insertExerciseAndExerciseCategoriesCrossRef(exercises.flatMap { exercise ->
//                exercise.category.map { category ->
//                    ExerciseAndExerciseCategoryCrossRef(
//                        exerciseId = exercise.id,
//                        exerciseCategoryId = category.id
//                    )
//                }
//            })
//        }
//
//
//        return exerciseRepository.getAllExercises()
//            .map { exercisesWithCategories -> exercisesWithCategories.map { it.toExercise() } }
//            .map { if (it.isEmpty()) Resource.Empty() else Resource.Success(it) }
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


    fun ExerciseCategory.toEntity() = ExerciseCategoryEntity(
        exerciseCategoryId = this.id,
        category = this.category,
        isSelected = isSelected
    )
}