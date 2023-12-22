package com.example.gymapp.data.repositories.local.schedule

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.gymapp.data.db.entities.ScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

    @Upsert
    suspend fun insertSchedule(schedule: ScheduleEntity){
        val filteredSchedules = getAllSchedules().filter { it.id == schedule.id }
        if(filteredSchedules.isNotEmpty()){

        }
    }

    @Upsert
    suspend fun insertSchedules(schedules: List<ScheduleEntity>)

    @Transaction
    @Query("SELECT * FROM schedules")
    suspend fun getAllSchedules() : List<ScheduleEntity>

    @Transaction
    @Query("SELECT * FROM schedules WHERE workoutId = :workoutId")
    suspend fun getSchedulesByWorkoutId(workoutId: Long) : List<ScheduleEntity>

    @Query("DELETE FROM schedules WHERE id = :scheduleId")
    suspend fun deleteScheduleById(scheduleId : Long)
}