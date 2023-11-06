package com.example.gymapp.data.repositories.workout

import androidx.room.Dao
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
    suspend fun upsertWorkouts(workouts: List<WorkoutEntity>) {
        for (workout in workouts) {
            val existingWorkout = getWorkoutById(workout.workoutId)
            if (existingWorkout?.date != null) {
                //do nothing
            } else {
                insertWorkout(workout)
            }
        }
    }


    @Query("SELECT * FROM workouts WHERE workoutId = :workoutId")
    suspend fun getWorkoutById(workoutId: Long): WorkoutEntity?


    @Upsert
    suspend fun insertExerciseWorkouts(exerciseWorkoutEntities: List<ExerciseWorkoutEntity>)

    @Transaction
    @Query("SELECT * FROM  workouts")
    fun getAllWorkouts(): Flow<List<WorkoutWithExerciseWorkoutPair>>

    @Transaction
    @Query("DELETE FROM workouts WHERE date == null")
    suspend fun deleteAllWorkouts()


    @Transaction
    @Query("DELETE FROM exerciseWorkout")
    suspend fun deleteExerciseWorkouts()

    @Update
    suspend fun updateWorkout(workout: WorkoutEntity)

    @Upsert
    suspend fun insertWorkoutAndExerciseWorkoutCrossRef(crossRefs: List<WorkoutAndExerciseWorkoutCrossRef>)


}