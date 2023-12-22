package com.example.gymapp.data.repositories.local.schedule

import com.example.gymapp.data.db.entities.ScheduleEntity
import com.example.gymapp.data.db.models.local.Schedule
import com.example.gymapp.data.db.models.local.WorkoutLocal
import com.example.gymapp.data.repositories.local.workout.DataWorkoutService
import com.example.gymapp.domain.workouts.DomainWorkoutService
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

    suspend fun getAllSchedules() = scheduleRepository.getAllSchedules().map { it.toSchedule() }


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
            workout = dataWorkoutService.getWorkoutById(this.id) ?: WorkoutLocal(),
            date = this.date,
            startTime = this.startTime,
            endTime = this.endTime,
            color = this.color
        )
    }
}