package com.example.gymapp.data.db.entities

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "schedules")
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val workoutId: Long,
    var date: LocalDate = LocalDate.now(),
    var startTime: LocalTime? = null,
    var endTime: LocalTime? = null,
    var color: Int = Color.Blue.toArgb()
)