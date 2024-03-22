package com.example.gymapp.data.repositories.local.schedule

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.gymapp.data.db.entities.ScheduleEntity
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
import com.example.gymapp.data.db.models.local.Schedule
import com.example.gymapp.data.db.models.local.WorkoutLocal
=======
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

    @Upsert
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
    suspend fun insertSchedule(schedule: ScheduleEntity)

    @Upsert
    suspend fun insertSchedules(schedules: List<ScheduleEntity>)
    @Transaction
    @Query("SELECT * FROM schedules")
    fun getAllSchedules() : Flow<List<ScheduleEntity>>
=======
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes

    @Transaction
    @Query("SELECT * FROM schedules WHERE workoutId = :workoutId")
    suspend fun getSchedulesByWorkoutId(workoutId: Long) : List<ScheduleEntity>

    @Query("DELETE FROM schedules WHERE id = :scheduleId")
    suspend fun deleteScheduleById(scheduleId : Long)
}