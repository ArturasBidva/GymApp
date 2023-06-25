package com.example.gymapp.data.repositories

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.gymapp.data.db.entities.ExerciseAndExerciseCategoryCrossRef
import com.example.gymapp.data.db.entities.ExerciseCategoryEntity
import com.example.gymapp.data.db.entities.ExerciseEntity
import javax.inject.Inject

class ExerciseRepository @Inject constructor(
    private val exerciseDao: ExerciseDao
) {


    fun getAllExercises() = exerciseDao.getAllExercises()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercises: ExerciseEntity) {
        exerciseDao.insertExercise(exercises)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<ExerciseEntity>) {
        exerciseDao.insertExercises(exercises)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseCategory(exerciseCategoryEntity: ExerciseCategoryEntity){
     exerciseDao.insertExerciseCategory(exerciseCategoryEntity)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseCategories(exerciseCategoryEntity: List<ExerciseCategoryEntity>){
        exerciseDao.insertExerciseCategories(exerciseCategoryEntity)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseAndExerciseCategoryCrossRefs(exerciseAndExerciseCategoryCrossRef: List<ExerciseAndExerciseCategoryCrossRef>){
        exerciseDao.insertExerciseAndExerciseCategoryCrossRefs(exerciseAndExerciseCategoryCrossRef)
    }

//    suspend fun insertExercise(exercise: ExerciseAndExerciseCategoryCrossRef) {
//        exerciseDao.upsert(exercise)
//    }
}