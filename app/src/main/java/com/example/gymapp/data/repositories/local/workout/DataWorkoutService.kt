package com.example.gymapp.data.repositories.local.workout

import com.example.gymapp.data.db.entities.WorkoutEntity
import com.example.gymapp.data.db.models.local.WorkoutLocal
import javax.inject.Inject

class DataWorkoutService @Inject constructor(private val dataWorkoutRepository: DataWorkoutRepository) {
   suspend fun getWorkoutById(workoutId: Long): WorkoutLocal?{

        return dataWorkoutRepository.getWorkoutById(workoutId = workoutId)?.toWorkoutLocal()
    }


    private fun WorkoutEntity.toWorkoutLocal() : WorkoutLocal{
        return WorkoutLocal(
            id = this.id,
            description = this.description,
            title = this.title,

        )
    }
}

