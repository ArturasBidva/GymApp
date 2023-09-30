package com.example.gymapp.ui.screens.workout

import com.example.gymapp.data.db.entities.ExerciseWorkoutEntity
import com.example.gymapp.data.db.entities.WorkoutAndExerciseWorkoutCrossRef
import com.example.gymapp.data.db.entities.WorkoutEntity
import com.example.gymapp.data.repositories.MyRepository
import com.example.gymapp.data.repositories.WorkoutRepository
import com.example.gymapp.domain.workouts.ExerciseWorkout
import com.example.gymapp.domain.workouts.Workout
import javax.inject.Inject

class WorkoutService @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val repository: MyRepository
) {

    private suspend fun updateWorkoutsFromAPI(): Boolean {
        val workoutResponse = workoutRepository.getWorkoutsFromApi()
        if (workoutResponse.isSuccessful) {
            val workouts = workoutResponse.body()!!
            workoutRepository.deleteWorkouts()
            workoutRepository.insertWorkouts(workouts.map { it.toWorkoutEntity() })
            val exerciseWorkoutEntities =
                workouts.map { it.exerciseWorkouts }.flatten().map { it.toExerciseWorkoutEntity() }
            workoutRepository.insertExerciseWorkouts(exerciseWorkoutEntities)
            val refs = workouts.flatMap { workout ->
                workout.exerciseWorkouts.map { exerciseWorkout ->
                    WorkoutAndExerciseWorkoutCrossRef(
                        workoutId = workout.id,
                        exerciseWorkoutId = exerciseWorkout.id
                    )
                }
            }
            insertWorkoutAndExerciseWorkoutCrossRef(refs)
            return true
        } else {
            return false
        }
    }

    private suspend fun insertWorkoutAndExerciseWorkoutCrossRef(
        workoutAndExerciseWorkoutCrossRef: List<WorkoutAndExerciseWorkoutCrossRef>
    ) {
        workoutRepository.insertWorkoutAndExerciseWorkoutCrossRefs(workoutAndExerciseWorkoutCrossRef)
    }

    private fun Workout.toWorkoutEntity() = WorkoutEntity(
        workoutId = this.id,
        title = this.title,
        description = this.description,
    )

    private fun ExerciseWorkout.toExerciseWorkoutEntity() = ExerciseWorkoutEntity(
        exerciseWorkoutId = this.id,
        completedCount = this.completedCount,
        weight = this.weight,
        goal = this.goal,
    )

    private fun WorkoutEntity.toWorkout() = Workout(
        id = this.workoutId,
        title = this.title,
        description = this.description,
    )


}

