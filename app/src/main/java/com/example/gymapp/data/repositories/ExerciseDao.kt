package com.example.gymapp.data.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.gymapp.data.db.entities.ExerciseAndExerciseCategoryCrossRef
import com.example.gymapp.data.db.entities.ExerciseCategoryEntity
import com.example.gymapp.data.db.entities.ExerciseCategoryWithExercisePair
import com.example.gymapp.data.db.entities.ExerciseEntity
import com.example.gymapp.data.db.entities.ExerciseWithExerciseCategoryPair
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: ExerciseEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercise: List<ExerciseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseCategory(exerciseCategory: ExerciseCategoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseCategories(exerciseCategory: List<ExerciseCategoryEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseAndExerciseCategoryCrossRefs(crossRefs: List<ExerciseAndExerciseCategoryCrossRef>)

    @Transaction
    @Query("SELECT * FROM category WHERE category = :category")
    fun getExercisesOfExerciseCategory(category: String): Flow<List<ExerciseCategoryWithExercisePair>>

    @Transaction
    @Query("SELECT * FROM exercises WHERE title = :title")
    fun getExerciseCategoryOfExercises(title: String): Flow<List<ExerciseWithExerciseCategoryPair>>
    @Transaction
    @Query("SELECT * FROM  exercises")
    fun getAllExercises(): Flow<List<ExerciseWithExerciseCategoryPair>>

    @Delete
    suspend fun deleteExercise(exercise: ExerciseEntity)

}