package com.example.gymapp.di

import com.example.gymapp.data.repositories.Converters
import android.content.Context
import androidx.room.Room
import androidx.room.TypeConverters
import com.example.gymapp.data.db.GymDatabase
import com.example.gymapp.data.repositories.exercise.ExerciseDao
import com.example.gymapp.data.repositories.workout.WorkoutDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@TypeConverters(Converters::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideGymDatabase(
        @ApplicationContext appContext: Context
    ): GymDatabase {
        return Room.databaseBuilder(
            appContext,
            GymDatabase::class.java,
            "gym.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideExerciseDao(database: GymDatabase): ExerciseDao {
        return database.exerciseDao()
    }
    @Singleton
    @Provides
    fun provideWorkoutDao(database: GymDatabase): WorkoutDao {
        return database.workoutDao()
    }

}