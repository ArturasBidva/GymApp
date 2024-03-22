package com.example.gymapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey
    val id: Long = 0,
    val title: String,
<<<<<<< Updated upstream
    val description: String
=======
<<<<<<< HEAD
    val description: String,
=======
    val description: String
>>>>>>> 62d43d62b70740a5f2988a12d092cab355d1dd9f
>>>>>>> Stashed changes
)
