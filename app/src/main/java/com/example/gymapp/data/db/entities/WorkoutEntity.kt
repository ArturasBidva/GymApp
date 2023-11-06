package com.example.gymapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey
    val workoutId: Long = 0,
    val title: String,
    val description: String,
    val date : LocalDate?,
    val startTime : LocalTime?,
    val endTime : LocalTime?,
)