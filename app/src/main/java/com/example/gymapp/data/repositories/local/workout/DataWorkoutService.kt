package com.example.gymapp.data.repositories.local.workout

import com.example.gymapp.data.db.entities.WorkoutEntity
import com.example.gymapp.data.db.models.local.WorkoutLocal
import javax.inject.Inject

class DataWorkoutService @Inject constructor(private val dataWorkoutRepository: DataWorkoutRepository) {
<<<<<<< Updated upstream
   suspend fun getWorkoutById(workoutId: Long): WorkoutLocal?{
=======
<<<<<<< HEAD
    suspend fun getWorkoutById(workoutId: Long): WorkoutLocal? {
=======
   suspend fun getWorkoutById(workoutId: Long): WorkoutLocal?{
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes

        return dataWorkoutRepository.getWorkoutById(workoutId = workoutId)?.toWorkoutLocal()
    }


<<<<<<< Updated upstream
    private fun WorkoutEntity.toWorkoutLocal() : WorkoutLocal{
=======
<<<<<<< HEAD
    private fun WorkoutEntity.toWorkoutLocal(): WorkoutLocal {
=======
    private fun WorkoutEntity.toWorkoutLocal() : WorkoutLocal{
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes
        return WorkoutLocal(
            id = this.id,
            description = this.description,
            title = this.title,

<<<<<<< Updated upstream
        )
=======
<<<<<<< HEAD
            )
=======
        )
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes
    }
}

