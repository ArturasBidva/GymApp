package com.example.gymapp.di

import android.content.Context
import androidx.room.Room
import com.example.gymapp.GymDatabase
import com.example.gymapp.data.db.TestDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

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

    //    @Provides
//    fun provideExerciseDao(database: GymDatabase): ExerciseDao {
//        return database.getExerciseDao()
//    }

    @Provides
    fun provideTestDao(database: GymDatabase): TestDao {
        return database.getTestDao()
    }
}