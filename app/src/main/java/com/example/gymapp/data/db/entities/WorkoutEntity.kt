package com.example.gymapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gymapp.data.local.Schedule
import java.time.LocalDate
import java.time.LocalTime
@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey
    val id: Long = 0,
    val title: String,
    val description: String,
    var schedules: List<Schedule> = emptyList(),
    val color: Int? = null
)