package com.example.gymapp.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "exerciseWorkout",
    foreignKeys = [ForeignKey(
        entity = ExerciseEntity::class,
        parentColumns = ["exerciseId"],
        childColumns = ["exerciseId"],
        onDelete = ForeignKey.CASCADE // Specify the behavior on delete
    )]
)
data class ExerciseWorkoutEntity(
    @PrimaryKey
    val exerciseWorkoutId: Long = 0,
    val completedCount: Int = 0,
    val weight: Int = 0,
    val goal: Int = 0,
    val exerciseId: Long
) {
    override fun toString(): String {
        return "ExerciseWorkoutEntity(exerciseWorkoutId=$exerciseWorkoutId, " +
                "completedCount=$completedCount, weight=$weight, goal=$goal, exerciseId=$exerciseId)"
    }
}