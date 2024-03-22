package com.example.gymapp.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutWithSchedules(
    @Embedded val workout: WorkoutEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "workoutId"
    )
    val schedules: List<ScheduleEntity>
)