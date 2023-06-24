package com.example.gymapp.data.repositories

import com.example.gymapp.data.db.TestDao
import com.example.gymapp.data.db.entities.Testas
import javax.inject.Inject

class ExerciseRepository @Inject constructor(
    private val exerciseDao: TestDao
) {

    suspend fun amogus() = exerciseDao.upsert(Testas("Amogus"))

//    fun getAllExercises() = exerciseDao.getAllExercises()
//
//    suspend fun insertExercises(exercises: List<ExerciseEntity>) {
//        exerciseDao.insertAll(exercises)
//    }
//
//    suspend fun insertExercise(exercise: ExerciseEntity) {
//        exerciseDao.upsert(exercise)
//    }
}