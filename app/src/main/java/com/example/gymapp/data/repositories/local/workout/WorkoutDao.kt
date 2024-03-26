package com.example.gymapp.data.repositories.local.workout

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.example.gymapp.data.db.entities.ExerciseWorkoutEntity
import com.example.gymapp.data.db.entities.WorkoutAndExerciseWorkoutCrossRef
import com.example.gymapp.data.db.entities.WorkoutEntity
import com.example.gymapp.data.db.entities.WorkoutWithExerciseWorkoutPair
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Upsert
    suspend fun insertWorkout(workout: WorkoutEntity)

    @Upsert
    suspend fun insertWorkouts(workouts: List<WorkoutEntity>)

    @Transaction
    @Query("SELECT * FROM workouts")
    fun getWorkoutWithExerciseWorkoutById(): Flow<List<WorkoutWithExerciseWorkoutPair>>


    @Upsert
    suspend fun insertExerciseWorkouts(exerciseWorkoutEntities: List<ExerciseWorkoutEntity>)

    @Transaction
    @Query("SELECT * FROM  workouts")
    fun getAllWorkouts(): Flow<List<WorkoutWithExerciseWorkoutPair>>


    @Transaction
    @Query("DELETE FROM exerciseWorkout")
    suspend fun deleteExerciseWorkouts()

    @Transaction
    @Query("DELETE FROM workouts where id = :workoutId")
    suspend fun deleteWorkoutById(workoutId: Long)


    @Update
    suspend fun updateWorkout(workout: WorkoutEntity)

    @Upsert
    suspend fun insertWorkoutAndExerciseWorkoutCrossRef(crossRefs: List<WorkoutAndExerciseWorkoutCrossRef>)

    @Transaction
    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    suspend fun getWorkoutById(workoutId: Long): WorkoutEntity


}