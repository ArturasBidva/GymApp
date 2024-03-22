package com.example.gymapp.domain.workouts

import com.example.gymapp.data.api.ApiService
import javax.inject.Inject

class DomainWorkoutRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getWorkoutsFromApi() = apiService.getAllWorkouts()
    }