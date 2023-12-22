package com.example.gymapp.data.repositories.local.schedule

import com.example.gymapp.data.db.entities.ScheduleEntity
import javax.inject.Inject

class ScheduleRepository @Inject constructor(
    private val scheduleDao: ScheduleDao
) {

    suspend fun insertSchedule(schedule: ScheduleEntity) {
        scheduleDao.insertSchedule(schedule = schedule)
    }

    suspend fun insertSchedules(scheduleEntities: List<ScheduleEntity>) {
        scheduleDao.insertSchedules(schedules = scheduleEntities)
    }

    suspend fun getAllSchedules(): List<ScheduleEntity> {
       return scheduleDao.getAllSchedules()
    }

    suspend fun deleteScheduleById(scheduleId: Long) {
        scheduleDao.deleteScheduleById(scheduleId = scheduleId)
    }

    suspend fun getScheduleByWorkoutId(workoutId: Long) =
        scheduleDao.getSchedulesByWorkoutId(workoutId = workoutId)
}