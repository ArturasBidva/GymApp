package com.example.gymapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gymapp.data.db.TestDao
import com.example.gymapp.data.db.entities.Testas

@Database(
    entities = [
        Testas::class
    ],
    version = 2,
    exportSchema = false
)
abstract class GymDatabase : RoomDatabase() {
//    abstract fun getExerciseDao(): ExerciseDao
    abstract fun getTestDao(): TestDao

}