package com.example.gymapp.data.repositories.local.schedule

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.example.gymapp.data.db.entities.ScheduleEntity
import com.example.gymapp.data.db.models.local.Schedule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDate

@Dao
interface ScheduleDao {

    @Upsert
    suspend fun insertSchedule(schedule: ScheduleEntity){
        val existingSchedule = getSchedulesByDateAndWorkoutId(schedule.date, schedule.workoutId)

        if (existingSchedule != null) {
            updateExistingSchedule(schedule)
        } else {
            insertNewSchedule(schedule)
        }
    }

    @Upsert
    suspend fun insertSchedules(schedules: List<ScheduleEntity>)

    @Transaction
    @Query("SELECT * FROM schedules")
    fun getAllSchedules() : Flow<List<ScheduleEntity>>

    @Insert
    suspend fun insertNewSchedule(schedule: ScheduleEntity)

    @Update
    suspend fun updateExistingSchedule(schedule: ScheduleEntity)

    @Query("DELETE FROM schedules WHERE date = :date AND workoutId = :workoutId")
    suspend fun deleteSchedulesByDateAndWorkoutId(date: LocalDate, workoutId: Long)

    @Transaction
    @Query("SELECT * FROM schedules WHERE date = :date AND workoutId = :workoutId")
    suspend fun getSchedulesByDateAndWorkoutId(date: LocalDate, workoutId: Long): ScheduleEntity?

}