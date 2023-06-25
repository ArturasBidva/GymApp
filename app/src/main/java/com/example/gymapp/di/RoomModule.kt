package com.example.gymapp.di

import android.content.Context
import androidx.room.Room
import com.example.gymapp.data.db.GymDatabase
import com.example.gymapp.data.repositories.ExerciseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
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

}