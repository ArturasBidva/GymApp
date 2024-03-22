package com.example.gymapp.data.repositories.local.schedule

import com.example.gymapp.data.db.entities.ScheduleEntity
import com.example.gymapp.data.db.models.local.Schedule
import com.example.gymapp.data.db.models.local.WorkoutLocal
import com.example.gymapp.data.repositories.local.workout.DataWorkoutService
import com.example.gymapp.domain.workouts.DomainWorkoutService
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
import kotlinx.coroutines.flow.map
=======
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes
import javax.inject.Inject

class ScheduleService @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val dataWorkoutService: DataWorkoutService
) {
    suspend fun insertSchedule(schedule: Schedule) {
        val scheduleEntity = schedule.toScheduleEntity()
        scheduleRepository.insertSchedule(scheduleEntity)
    }


    suspend fun deleteScheduleById(scheduleId: Long) {
        scheduleRepository.deleteScheduleById(scheduleId)
    }

<<<<<<< Updated upstream
    suspend fun getAllSchedules() = scheduleRepository.getAllSchedules().map { it.toSchedule() }
=======
<<<<<<< HEAD
    suspend fun getAllSchedules() = scheduleRepository.getAllSchedules().map { it -> it.map { it.toSchedule() }  }
=======
    suspend fun getAllSchedules() = scheduleRepository.getAllSchedules().map { it.toSchedule() }
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes


    private fun Schedule.toScheduleEntity(): ScheduleEntity {
        return ScheduleEntity(
            workoutId = this.workout.id,
            date = this.date,
            startTime = this.startTime,
            endTime = this.endTime,
            color = this.color
        )
    }

    private suspend fun ScheduleEntity.toSchedule(): Schedule {
        return Schedule(
<<<<<<< Updated upstream
            workout = dataWorkoutService.getWorkoutById(this.id) ?: WorkoutLocal(),
=======
<<<<<<< HEAD
            id = this.id,
            workout = dataWorkoutService.getWorkoutById(this.workoutId) ?: WorkoutLocal(),
=======
            workout = dataWorkoutService.getWorkoutById(this.id) ?: WorkoutLocal(),
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes
            date = this.date,
            startTime = this.startTime,
            endTime = this.endTime,
            color = this.color
        )
    }
}