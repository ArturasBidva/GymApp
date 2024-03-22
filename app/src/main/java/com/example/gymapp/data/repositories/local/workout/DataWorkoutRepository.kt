package com.example.gymapp.data.repositories.local.workout

<<<<<<< Updated upstream
=======
<<<<<<< HEAD
import com.example.gymapp.data.db.entities.ExerciseWorkoutEntity
import com.example.gymapp.data.db.entities.WorkoutAndExerciseWorkoutCrossRef
import com.example.gymapp.data.db.entities.WorkoutEntity
import com.example.gymapp.data.repositories.local.schedule.ScheduleDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
=======
>>>>>>> Stashed changes
import com.example.gymapp.data.api.ApiService
import com.example.gymapp.data.db.entities.ExerciseWorkoutEntity
import com.example.gymapp.data.db.entities.WorkoutAndExerciseWorkoutCrossRef
import com.example.gymapp.data.db.entities.WorkoutEntity
<<<<<<< Updated upstream
=======
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes
import javax.inject.Inject

class DataWorkoutRepository @Inject constructor(
    private val workoutDao: WorkoutDao,
<<<<<<< Updated upstream
    private val apiService: ApiService
=======
<<<<<<< HEAD
    private val scheduleDao: ScheduleDao
=======
    private val apiService: ApiService
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes
) {

    suspend fun insertWorkouts(workouts: List<WorkoutEntity>) {
        workoutDao.insertWorkouts(workouts = workouts)
    }

    suspend fun insertExerciseWorkouts(exerciseWorkout: List<ExerciseWorkoutEntity>) {
        workoutDao.insertExerciseWorkouts(exerciseWorkout)
    }


   suspend fun getWorkoutById(workoutId : Long) = workoutDao.getWorkoutById(workoutId = workoutId)



    fun getAllWorkouts() = workoutDao.getAllWorkouts()

<<<<<<< Updated upstream
=======
<<<<<<< HEAD
    suspend fun deleteWorkoutsNotInSchedules() {
        val scheduleIds = scheduleDao.getAllSchedules()
            .map { schedules ->
                schedules.map { it.workoutId }
            }
            .firstOrNull() ?: return // Exit if no schedules found or if the list is empty

        val workoutsToDelete = workoutDao.getWorkoutsNotInSchedules(scheduleIds)

        if (workoutsToDelete.isNotEmpty()) {
            workoutDao.deleteWorkoutsById(workoutsToDelete.map { it.id })
        }
    }
=======
>>>>>>> Stashed changes

//    suspend fun deleteWorkouts() {
//        workoutDao.deleteAllWorkouts()
//    }

<<<<<<< Updated upstream
=======
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes

    suspend fun insertWorkoutAndExerciseWorkoutCrossRefs(
        workoutAndExerciseWorkoutCrossRef: List<WorkoutAndExerciseWorkoutCrossRef>
    ) {
        workoutDao.insertWorkoutAndExerciseWorkoutCrossRef(workoutAndExerciseWorkoutCrossRef)
    }
}