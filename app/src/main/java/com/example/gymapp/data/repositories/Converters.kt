package com.example.gymapp.data.repositories

import androidx.room.TypeConverter
import com.example.gymapp.data.db.entities.ExerciseCategoryEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.LocalTime


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

    @TypeConverter
    fun toDate(dateString: String?): LocalDate? {
        return if (dateString == null) {
            null
        } else {
            LocalDate.parse(dateString)
        }
    }

    @TypeConverter
    fun toDateString(date: LocalDate?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun toTime(dateString: String?): LocalTime? {
        return if (dateString == null) {
            null
        } else {
            LocalTime.parse(dateString)
        }
    }

    @TypeConverter
    fun toTimeString(date: LocalTime?): String? {
        return date?.toString()
    }
}
