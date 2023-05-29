package com.example.gymapp.workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.models.Workout
import com.example.gymapp.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val repository: MyRepository
) : ViewModel() {
    private val _workouts = MutableLiveData<List<Workout>>(emptyList())
    open val workouts: LiveData<List<Workout>> = _workouts
    private val _workout = MutableLiveData<Workout>(null)
    open val workout: LiveData<Workout> = _workout

    init {
        getAllWorkouts()
    }

    private fun getAllWorkouts() {
        viewModelScope.launch {
            _workouts.postValue(repository.getAllWorkouts())
        }
    }

    fun getWorkoutById(id: Long) {
        viewModelScope.launch {
            _workout.postValue(repository.getWorkoutById(id))
        }
    }

    fun createWorkout(workout: Workout) {
        viewModelScope.launch {
            val isSuccessful = repository.createWorkout(workout)
            if (isSuccessful) {
                getAllWorkouts()
            }
        }
    }
}
