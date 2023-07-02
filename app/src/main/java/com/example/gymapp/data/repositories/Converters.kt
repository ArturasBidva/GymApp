package com.example.gymapp.data.repositories

import androidx.room.TypeConverter
import com.example.gymapp.data.db.entities.ExerciseCategoryEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromExerciseCategoryEntity(exerciseCategoryEntity: List<ExerciseCategoryEntity>): String {
        return gson.toJson(exerciseCategoryEntity)
    }

    @TypeConverter
    fun toExerciseCategoryEntity(data: String): List<ExerciseCategoryEntity> {
        val listType = object : TypeToken<List<ExerciseCategoryEntity>>() {
        }.type
        return gson.fromJson(data, listType)
    }
}
