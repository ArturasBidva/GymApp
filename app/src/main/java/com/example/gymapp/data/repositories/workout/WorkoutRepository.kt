package com.example.gymapp.data.repositories.workout

import com.example.gymapp.data.api.ApiService
import com.example.gymapp.data.db.entities.ExerciseWorkoutEntity
import com.example.gymapp.data.db.entities.WorkoutAndExerciseWorkoutCrossRef
import com.example.gymapp.data.db.entities.WorkoutEntity
import javax.inject.Inject

class WorkoutRepository @Inject constructor(
    private val workoutDao: WorkoutDao,
    private val apiService: ApiService
) {

    suspend fun insertWorkouts(workouts: List<WorkoutEntity>) {
        workoutDao.upsertWorkouts(workouts = workouts)
    }

    suspend fun insertExerciseWorkouts(exerciseWorkout: List<ExerciseWorkoutEntity>) {
        workoutDao.insertExerciseWorkouts(exerciseWorkout)
    }



    fun getAllWorkouts() = workoutDao.getAllWorkouts()

    suspend fun getWorkoutsFromApi() = apiService.getAllWorkouts()

    suspend fun deleteWorkouts() {
        workoutDao.deleteAllWorkouts()
    }

    suspend fun addWorkoutToSchedule(workout: WorkoutEntity){
        workoutDao.updateWorkout(workout = workout)
    }


    suspend fun deleteExerciseWorkouts() {
        workoutDao.deleteExerciseWorkouts()
    }

    suspend fun insertWorkoutAndExerciseWorkoutCrossRefs(
        workoutAndExerciseWorkoutCrossRef: List<WorkoutAndExerciseWorkoutCrossRef>
    ) {
        workoutDao.insertWorkoutAndExerciseWorkoutCrossRef(workoutAndExerciseWorkoutCrossRef)
    }
}