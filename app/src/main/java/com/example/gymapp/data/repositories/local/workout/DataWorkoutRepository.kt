package com.example.gymapp.data.repositories.local.workout

import com.example.gymapp.data.api.ApiService
import com.example.gymapp.data.db.entities.ExerciseWorkoutEntity
import com.example.gymapp.data.db.entities.WorkoutAndExerciseWorkoutCrossRef
import com.example.gymapp.data.db.entities.WorkoutEntity
import com.example.gymapp.data.db.entities.WorkoutWithExerciseWorkoutPair
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataWorkoutRepository @Inject constructor(
    private val workoutDao: WorkoutDao
) {

    suspend fun insertWorkouts(workouts: List<WorkoutEntity>) {
        workoutDao.insertWorkouts(workouts = workouts)
    }

    suspend fun deleteWorkoutById(workoutId: Long){
        workoutDao.deleteWorkoutById(workoutId = workoutId)
    }

    suspend fun insertExerciseWorkouts(exerciseWorkout: List<ExerciseWorkoutEntity>) {
        workoutDao.insertExerciseWorkouts(exerciseWorkout)
    }


    suspend fun getWorkouts(): Flow<List<WorkoutWithExerciseWorkoutPair>> {
        return workoutDao.getWorkoutWithExerciseWorkoutById()
    }


    suspend fun insertWorkoutAndExerciseWorkoutCrossRefs(
        workoutAndExerciseWorkoutCrossRef: List<WorkoutAndExerciseWorkoutCrossRef>
    ) {
        workoutDao.insertWorkoutAndExerciseWorkoutCrossRef(workoutAndExerciseWorkoutCrossRef)
    }
}