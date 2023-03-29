package com.example.gymapp.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.repository.MyRepository
import com.example.gymapp.models.Exercise
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val repository: MyRepository

) : ViewModel() {
    private val _exercises = MutableLiveData<List<Exercise>>(emptyList())
    open val exercises: LiveData<List<Exercise>> = _exercises

    fun getAllExercises() {
        viewModelScope.launch {
            _exercises.postValue(repository.getAllExercises())
        }
    }
}

