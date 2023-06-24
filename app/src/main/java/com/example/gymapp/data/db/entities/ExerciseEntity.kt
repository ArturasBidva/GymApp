package com.example.gymapp.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey
    val id: Long = 0,
    val title: String,
    val weight: Int = 0,
    val imgUrl: String,
    val description: String,
    @Relation(
        entity = ExerciseCategoryEntity::class,
        parentColumn = "id",
        entityColumn = "id"
    )
    val categories: List<ExerciseCategoryEntity>
)