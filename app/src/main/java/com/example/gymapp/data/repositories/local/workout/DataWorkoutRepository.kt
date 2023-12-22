package com.example.gymapp.data.repositories.local.workout

import com.example.gymapp.data.api.ApiService
import com.example.gymapp.data.db.entities.ExerciseWorkoutEntity
import com.example.gymapp.data.db.entities.WorkoutAndExerciseWorkoutCrossRef
import com.example.gymapp.data.db.entities.WorkoutEntity
import javax.inject.Inject

class DataWorkoutRepository @Inject constructor(
    private val workoutDao: WorkoutDao,
    private val apiService: ApiService
) {

    suspend fun insertWorkouts(workouts: List<WorkoutEntity>) {
        workoutDao.insertWorkouts(workouts = workouts)
    }

    suspend fun insertExerciseWorkouts(exerciseWorkout: List<ExerciseWorkoutEntity>) {
        workoutDao.insertExerciseWorkouts(exerciseWorkout)
    }


   suspend fun getWorkoutById(workoutId : Long) = workoutDao.getWorkoutById(workoutId = workoutId)



    fun getAllWorkouts() = workoutDao.getAllWorkouts()


//    suspend fun deleteWorkouts() {
//        workoutDao.deleteAllWorkouts()
//    }


    suspend fun insertWorkoutAndExerciseWorkoutCrossRefs(
        workoutAndExerciseWorkoutCrossRef: List<WorkoutAndExerciseWorkoutCrossRef>
    ) {
        workoutDao.insertWorkoutAndExerciseWorkoutCrossRef(workoutAndExerciseWorkoutCrossRef)
    }
}