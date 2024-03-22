package com.example.gymapp.data.repositories.local.exercise

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.gymapp.data.db.entities.ExerciseAndExerciseCategoryCrossRef
import com.example.gymapp.data.db.entities.ExerciseCategoryEntity
import com.example.gymapp.data.db.entities.ExerciseEntity
import com.example.gymapp.data.db.entities.ExerciseWithCategoryPair
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Upsert
    suspend fun insertExercises(exercise: List<ExerciseEntity>)

    @Upsert
    suspend fun insertExerciseCategories(exerciseCategory: List<ExerciseCategoryEntity>)

    @Upsert
    suspend fun insertExerciseAndExerciseCategoryCrossRefs(crossRefs: List<ExerciseAndExerciseCategoryCrossRef>)

    @Transaction
    @Query("SELECT * FROM exercise_categories")
    fun getExerciseCategories(): Flow<List<ExerciseCategoryEntity>>

    @Transaction
    @Query("SELECT * FROM exercises")
    fun getExercisesWithCategories(): Flow<List<ExerciseWithCategoryPair>>

    @Transaction
    @Query("DELETE FROM exercises")
    suspend fun deleteAllExercises()

    @Transaction
    @Query("SELECT * FROM exercises WHERE exerciseId = :exerciseId")
    fun getExerciseById(exerciseId: Long): ExerciseWithCategoryPair

    @Transaction
    @Query("DELETE FROM exercise_categories")
    suspend fun deleteAllCategories()

}