package com.example.gymapp.data.repositories

import androidx.room.TypeConverter
import com.example.gymapp.domain.exercises.ExerciseCategory
import com.google.gson.Gson

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromExerciseCategory(exerciseCategory: ExerciseCategory): String {
        return gson.toJson(exerciseCategory)
    }

    @TypeConverter
    fun toExerciseCategory(data: String): ExerciseCategory {
        return gson.fromJson(data, ExerciseCategory::class.java)
    }
}