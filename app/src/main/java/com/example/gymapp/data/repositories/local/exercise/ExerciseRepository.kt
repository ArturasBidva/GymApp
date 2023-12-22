package com.example.gymapp.data.repositories.local.exercise

import com.example.gymapp.data.api.ApiService
import com.example.gymapp.data.db.entities.ExerciseAndExerciseCategoryCrossRef
import com.example.gymapp.data.db.entities.ExerciseCategoryEntity
import com.example.gymapp.data.db.entities.ExerciseEntity
import javax.inject.Inject

class ExerciseRepository @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val apiService: ApiService
) {

    fun getAllExercises() = exerciseDao.getAllExercises()

    fun getAllExerciseCategories() = exerciseDao.getAllExerciseCategories()

    suspend fun getExercisesFromApi() = apiService.getAllExercises()
    suspend fun getCategoriesFromApi() = apiService.getExerciseCategories()

    suspend fun deleteExercises() {
        exerciseDao.deleteAllExercises()
    }

    suspend fun deleteCategories() {
        exerciseDao.deleteAllCategories()
    }

    suspend fun insertExercises(exercises: List<ExerciseEntity>) {
        exerciseDao.insertExercises(exercises)
    }

    suspend fun insertExerciseCategories(exerciseCategoryEntity: List<ExerciseCategoryEntity>) {
        exerciseDao.insertExerciseCategories(exerciseCategoryEntity)
    }

    suspend fun insertExerciseAndExerciseCategoryCrossRefs(
        exerciseAndExerciseCategoryCrossRef: List<ExerciseAndExerciseCategoryCrossRef>
    ) {
        exerciseDao.insertExerciseAndExerciseCategoryCrossRefs(exerciseAndExerciseCategoryCrossRef)
    }

}