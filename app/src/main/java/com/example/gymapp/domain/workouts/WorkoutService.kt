package com.example.gymapp.domain.workouts

import com.example.gymapp.data.db.entities.ExerciseWorkoutEntity
import com.example.gymapp.data.db.entities.WorkoutAndExerciseWorkoutCrossRef
import com.example.gymapp.data.db.entities.WorkoutEntity
import com.example.gymapp.data.db.entities.WorkoutWithExerciseWorkoutPair
import com.example.gymapp.data.repositories.MyRepository
import com.example.gymapp.data.repositories.workout.WorkoutRepository
import com.example.gymapp.domain.exercises.ExerciseService
import com.example.gymapp.util.Resource
import com.example.gymapp.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WorkoutService @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val exerciseService: ExerciseService,
    private val repository: MyRepository
) {

    suspend fun syncWorkoutDataWithAPI(): Boolean {
        exerciseService.syncDataWithAPI()
        val workoutResponse = workoutRepository.getWorkoutsFromApi()
        if (workoutResponse.isSuccessful) {
            val workouts = workoutResponse.body()!!
            workoutRepository.deleteWorkouts()
            workoutRepository.deleteExerciseWorkouts()
            workoutRepository.insertWorkouts(workouts.map { it.toWorkoutEntity() })
            val exerciseWorkoutEntities =
                workouts.map { it.exerciseWorkouts }.flatten().map { it.toExerciseWorkoutEntity() }
            workoutRepository.insertExerciseWorkouts(exerciseWorkoutEntities)
            val refs = workouts.flatMap { workout ->
                workout.exerciseWorkouts.map { exerciseWorkout ->
                    WorkoutAndExerciseWorkoutCrossRef(
                        workoutId = workout.id!!,
                        exerciseWorkoutId = exerciseWorkout.id!!
                    )
                }
            }
            insertWorkoutAndExerciseWorkoutCrossRef(refs)
            return true
        } else {
            return false
        }
    }

    fun getWorkouts(): Flow<List<Workout>> {
        return workoutRepository.getAllWorkouts()
            .map { workoutWithExerciseWorkout ->
                workoutWithExerciseWorkout.map { it.toWorkout() }
            }
    }

    suspend fun addWorkoutToSchedule(workout: Workout){
        workoutRepository.addWorkoutToSchedule(workout = workout.toWorkoutEntity())
    }

    suspend fun deleteExerciseWorkoutFromWorkoutById(
        workoutId: Long,
        exerciseId: Long
    ): Resource<Unit> {
        val response = repository.deleteExerciseWorkoutFromWorkoutById(workoutId, exerciseId)
        return if (response is Resource.Success) {
            syncWorkoutDataWithAPI()
            Resource.Success(Unit)
        } else Resource.Error(UiText.DynamicString("Error deleting ExerciseWorkout"))
    }

    suspend fun deleteWorkoutById(workoutId: Long): Resource<Unit> {
        val response = repository.deleteWorkoutById(workoutId)
        return if (response is Resource.Success) {
            syncWorkoutDataWithAPI()
            Resource.Success(Unit)
        } else Resource.Error(UiText.DynamicString("Error deleting workout"))
    }

    suspend fun createWorkout(workout: Workout): Resource<Workout> {
        val response = repository.createWorkout(workout = workout)
        return if (response is Resource.Success) {
            syncWorkoutDataWithAPI()
            Resource.Success(workout)
        } else Resource.Error(UiText.DynamicString("Error creating workout"))
    }


    suspend fun addExerciseWorkoutToWorkout(
        workout: Workout,
        exerciseWorkout: ExerciseWorkout
    ): Boolean {
        val response = repository.addExerciseWorkoutToWorkout(workout, exerciseWorkout)
        return if (response is Resource.Success) {
            syncWorkoutDataWithAPI()
            return true
        } else {
            false
        }
    }

    private suspend fun insertWorkoutAndExerciseWorkoutCrossRef(
        workoutAndExerciseWorkoutCrossRef: List<WorkoutAndExerciseWorkoutCrossRef>
    ) {
        workoutRepository.insertWorkoutAndExerciseWorkoutCrossRefs(
            workoutAndExerciseWorkoutCrossRef
        )
    }

    private fun Workout.toWorkoutEntity() = WorkoutEntity(
        workoutId = this.id!!,
        title = this.title,
        description = this.description,
        endTime = this.endTime,
        date = this.date,
        startTime = this.startTime
    )

    private fun ExerciseWorkout.toExerciseWorkoutEntity() = ExerciseWorkoutEntity(
        exerciseWorkoutId = this.id!!,
        completedCount = this.completedCount,
        weight = this.weight,
        goal = this.goal,
        exerciseId = this.exercise.id
    )

    private suspend fun ExerciseWorkoutEntity.toExerciseWorkout(): ExerciseWorkout {
        val exercise = repository.getExerciseById(this.exerciseId)
        return ExerciseWorkout(
            id = this.exerciseWorkoutId,
            completedCount = this.completedCount,
            weight = this.weight,
            goal = this.goal,
            exercise = exercise
        )
    }

    private fun WorkoutEntity.toWorkout() = Workout(
        id = this.workoutId,
        title = this.title,
        description = this.description
    )

    private suspend fun WorkoutWithExerciseWorkoutPair.toWorkout() = Workout(
        id = this.workoutEntity.workoutId,
        title = this.workoutEntity.title,
        description = this.workoutEntity.description,
        exerciseWorkouts = this.exerciseWorkout.map { it.toExerciseWorkout() }
    )
}