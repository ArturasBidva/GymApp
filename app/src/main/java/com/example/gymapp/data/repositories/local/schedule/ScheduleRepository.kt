package com.example.gymapp.data.repositories.local.schedule

import com.example.gymapp.data.db.entities.ScheduleEntity
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
import com.example.gymapp.data.db.models.local.Schedule
import com.example.gymapp.data.db.models.local.WorkoutLocal
import kotlinx.coroutines.flow.Flow
=======
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes
import javax.inject.Inject

class ScheduleRepository @Inject constructor(
    private val scheduleDao: ScheduleDao
) {

    suspend fun insertSchedule(schedule: ScheduleEntity) {
        scheduleDao.insertSchedule(schedule = schedule)
<<<<<<< Updated upstream
=======
<<<<<<< HEAD

=======
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes
    }

    suspend fun insertSchedules(scheduleEntities: List<ScheduleEntity>) {
        scheduleDao.insertSchedules(schedules = scheduleEntities)
    }

<<<<<<< Updated upstream
    suspend fun getAllSchedules(): List<ScheduleEntity> {
       return scheduleDao.getAllSchedules()
    }
=======
<<<<<<< HEAD
    fun getAllSchedules() = scheduleDao.getAllSchedules()
=======
    suspend fun getAllSchedules(): List<ScheduleEntity> {
       return scheduleDao.getAllSchedules()
    }
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes

    suspend fun deleteScheduleById(scheduleId: Long) {
        scheduleDao.deleteScheduleById(scheduleId = scheduleId)
    }

    suspend fun getScheduleByWorkoutId(workoutId: Long) =
        scheduleDao.getSchedulesByWorkoutId(workoutId = workoutId)
}