package com.example.gymapp.data.db.entities

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
import androidx.room.ForeignKey
=======
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

<<<<<<< Updated upstream
@Entity(tableName = "schedules")
=======
<<<<<<< HEAD
@Entity(
    tableName = "schedules",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE // Deletes associated schedules when workout is deleted
        )
    ]
)
=======
@Entity(tableName = "schedules")
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val workoutId: Long,
    var date: LocalDate = LocalDate.now(),
    var startTime: LocalTime? = null,
    var endTime: LocalTime? = null,
    var color: Int = Color.Blue.toArgb()
)