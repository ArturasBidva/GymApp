package com.example.gymapp.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.models.Exercise
import com.example.gymapp.models.ExerciseCategory
import com.example.gymapp.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class ExerciseViewModel @Inject constructor(
    private val repository: MyRepository

) : ViewModel() {

    private val _exercises = MutableLiveData<List<Exercise>>(emptyList())
    open val exercises: LiveData<List<Exercise>> = _exercises

    init {
        getAllExercises()
    }

    fun getAllExercises() {
        viewModelScope.launch {
            _exercises.postValue(repository.getAllExercises())
        }
    }

    fun updateExercise(id: Long, exercise: Exercise) {
        viewModelScope.launch {
            val isSuccessful = repository.updateExercise(id, exercise)
            if (isSuccessful) {
                getAllExercises()
            }
        }
    }
    }

