package com.example.gymapp.data.db.entities

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    primaryKeys = ["date", "workoutId"],
    tableName = "schedules",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ScheduleEntity(
    val workoutId: Long,
    var date: LocalDate = LocalDate.now(),
    var startTime: LocalTime? = null,
    var endTime: LocalTime? = null,
    var color: Int = Color.Blue.toArgb()
)