package com.example.gymapp.domain.workouts

import com.example.gymapp.data.db.entities.ExerciseWorkoutEntity
import com.example.gymapp.data.db.entities.WorkoutAndExerciseWorkoutCrossRef
import com.example.gymapp.data.db.entities.WorkoutEntity
import com.example.gymapp.data.db.models.local.WorkoutLocal
import com.example.gymapp.data.repositories.MyRepository
import com.example.gymapp.data.repositories.local.workout.DataWorkoutRepository
import com.example.gymapp.domain.exercises.ExerciseService
import com.example.gymapp.util.Resource
import com.example.gymapp.util.UiText
import javax.inject.Inject

class DomainWorkoutService @Inject constructor(
    private val domainWorkoutRepository: DomainWorkoutRepository,
    private val dataWorkoutRepository: DataWorkoutRepository,
    private val exerciseService: ExerciseService,
    private val repository: MyRepository
) {

    suspend fun syncWorkoutDataWithAPI(): Boolean {
        exerciseService.syncDataWithAPI()
        val workoutResponse = domainWorkoutRepository.getWorkoutsFromApi()
        if (workoutResponse.isSuccessful) {
            val workouts = workoutResponse.body()!!
            dataWorkoutRepository.insertWorkouts(workouts.map { it.toWorkoutEntity() })
            val exerciseWorkoutEntities =
                workouts.map { it.exerciseWorkouts }.flatten().map { it.toExerciseWorkoutEntity() }
            dataWorkoutRepository.insertExerciseWorkouts(exerciseWorkoutEntities)
            val refs = workouts.flatMap { workout ->
                workout.exerciseWorkouts.map { exerciseWorkout ->
                    WorkoutAndExerciseWorkoutCrossRef(
                        id = workout.id!!,
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
            dataWorkoutRepository.deleteWorkoutById(workoutId = workoutId)
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
        workout: WorkoutLocal,
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

    private suspend fun insertWorkoutAndExerciseWorkoutCrossRef(workoutAndExerciseWorkoutCrossRef: List<WorkoutAndExerciseWorkoutCrossRef>) {
        dataWorkoutRepository.insertWorkoutAndExerciseWorkoutCrossRefs(
            workoutAndExerciseWorkoutCrossRef
        )
    }

    private fun Workout.toWorkoutEntity(): WorkoutEntity {
        return WorkoutEntity(
            id = this.id!!,
            title = this.title,
            description = this.description
        )
    }

    private fun ExerciseWorkout.toExerciseWorkoutEntity() = ExerciseWorkoutEntity(
        exerciseWorkoutId = this.id!!,
        completedCount = this.completedCount,
        weight = this.weight,
        goal = this.goal,
        exerciseId = this.exercise.id
    )

}