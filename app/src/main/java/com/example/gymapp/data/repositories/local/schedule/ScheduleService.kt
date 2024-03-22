package com.example.gymapp.data.repositories.local.schedule

import com.example.gymapp.data.db.entities.ScheduleEntity
import com.example.gymapp.data.db.models.local.Schedule
import com.example.gymapp.data.db.models.local.WorkoutLocal
import com.example.gymapp.data.repositories.local.workout.DataWorkoutService
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.singleOrNull
import java.time.LocalDate
import javax.inject.Inject

class ScheduleService @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val dataWorkoutService: DataWorkoutService
) {
    suspend fun insertSchedule(schedule: Schedule) {
        val scheduleEntity = schedule.toScheduleEntity()
        scheduleRepository.insertSchedule(scheduleEntity)
    }


    suspend fun deleteScheduleById(date: LocalDate, workoutId: Long) {
        scheduleRepository.deleteScheduleById(date = date, workoutId = workoutId)
    }

    suspend fun getAllSchedules() =
        scheduleRepository.getAllSchedules().map { schedule -> schedule.map { it.toSchedule() } }

    private suspend fun getWorkoutById(workoutId: Long): WorkoutLocal? {
        return dataWorkoutService.getAllWorkouts()
            .map { workouts -> workouts.firstOrNull { it.id == workoutId } }.singleOrNull()
    }

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
            workout = getWorkoutById(this.workoutId) ?: WorkoutLocal(),
            date = this.date,
            startTime = this.startTime,
            endTime = this.endTime,
            color = this.color
        )
    }
}