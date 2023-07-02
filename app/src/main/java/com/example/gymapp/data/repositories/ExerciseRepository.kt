package com.example.gymapp.data.repositories

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

    suspend fun insertExercise(exercises: ExerciseEntity) {
        exerciseDao.insertExercise(exercises)
    }

    suspend fun getExercisesFromApi() = apiService.getAllExercises()

    suspend fun deleteExercises(exercises: List<ExerciseEntity>) {
        exerciseDao.deleteExercises(exercises)
    }

    suspend fun deleteExercises() {
        exerciseDao.deleteAllExercises()
    }

    suspend fun deleteCategories() {
        exerciseDao.deleteAllCategories()
    }

    suspend fun deleteExercise(exercises: ExerciseEntity) {
        exerciseDao.deleteExercise(exercises)
    }

    suspend fun insertExercises(exercises: List<ExerciseEntity>) {
        exerciseDao.insertExercises(exercises)
    }

    suspend fun insertExerciseCategory(exerciseCategoryEntity: ExerciseCategoryEntity) {
        exerciseDao.insertExerciseCategory(exerciseCategoryEntity)
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