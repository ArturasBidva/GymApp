package com.example.gymapp.repository

import android.app.Application
import com.example.gymapp.R
import com.example.gymapp.api.ApiService
import com.example.gymapp.models.Exercise

interface MyRepository {
    suspend fun getAllExercises(): List<Exercise>
}

class MyRepositoryImpl(
    private val api: ApiService,
    private val appContext: Application
) : MyRepository {
    init {
        val appName = appContext.getString(R.string.app_name)
        println("Hello from $appName")
    }

    override suspend fun getAllExercises(): List<Exercise> {
       return api.getAllExercises()
    }
}
