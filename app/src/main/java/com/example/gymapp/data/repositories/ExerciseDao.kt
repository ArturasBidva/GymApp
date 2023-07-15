package com.example.gymapp.data.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.gymapp.data.db.entities.ExerciseAndExerciseCategoryCrossRef
import com.example.gymapp.data.db.entities.ExerciseCategoryEntity
import com.example.gymapp.data.db.entities.ExerciseCategoryWithExercisePair
import com.example.gymapp.data.db.entities.ExerciseEntity
import com.example.gymapp.data.db.entities.ExerciseWithExerciseCategoryPair
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Upsert
    suspend fun insertExercise(exercise: ExerciseEntity)

    @Upsert
    suspend fun insertExercises(exercise: List<ExerciseEntity>)

    @Upsert
    suspend fun insertExerciseCategory(exerciseCategory: ExerciseCategoryEntity)

    @Upsert
    suspend fun insertExerciseCategories(exerciseCategory: List<ExerciseCategoryEntity>)

    @Upsert
    suspend fun insertExerciseAndExerciseCategoryCrossRefs(crossRefs: List<ExerciseAndExerciseCategoryCrossRef>)

    @Transaction
    @Query("SELECT * FROM category WHERE category = :category")
    fun getExercisesOfExerciseCategory(category: String): Flow<List<ExerciseCategoryWithExercisePair>>

    @Transaction
    @Query("SELECT * FROM exercises WHERE title = :title")
    fun getExerciseCategoryOfExercises(title: String): Flow<List<ExerciseWithExerciseCategoryPair>>

    @Transaction
    @Query("SELECT * FROM category")
        fun getAllExerciseCategories(): Flow<List<ExerciseCategoryEntity>>

    @Transaction
    @Query("SELECT * FROM  exercises")
    fun getAllExercises(): Flow<List<ExerciseWithExerciseCategoryPair>>

    @Delete
    suspend fun deleteExercises(exercise: List<ExerciseEntity>)

    @Transaction
    @Query("DELETE FROM exercises")
    suspend fun deleteAllExercises()

    @Transaction
    @Query("DELETE FROM category")
    suspend fun deleteAllCategories()

    @Delete
    suspend fun deleteExercise(exercise: ExerciseEntity)

}