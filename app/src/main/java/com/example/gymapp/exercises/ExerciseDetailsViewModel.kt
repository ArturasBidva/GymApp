package com.example.gymapp.exercises

import androidx.compose.runtime.mutableStateListOf
import androidx.fragment.app.activityViewModels
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
class ExerciseDetailsViewModel @Inject constructor(
    private val repository: MyRepository
) : ViewModel() {
    private val _exercise = MutableLiveData<Exercise>(null)
    open val exercise: LiveData<Exercise> = _exercise
    private val _exercises = MutableLiveData<List<Exercise>>(emptyList())
    open val exercises: LiveData<List<Exercise>> = _exercises
    private val _exerciseCategories = MutableLiveData<List<ExerciseCategory>>(emptyList())
    open val exerciseCategories: LiveData<List<ExerciseCategory>> = _exerciseCategories
    private val _createdExerciseId = MutableLiveData<Int>()
    val createdExerciseId: LiveData<Int> get() = _createdExerciseId

    init {
        getAllExercises()
        getAllExerciseCategories()
    }

    private fun getAllExercises() {
        viewModelScope.launch {
            _exercises.postValue(repository.getAllExercises())
        }
    }

    private fun getAllExerciseCategories(){
        viewModelScope.launch {
            _exerciseCategories.postValue(repository.getAllCategories())
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

    fun getExerciseById(id: Long) {
        viewModelScope.launch {
            repository.getExerciseById(id)
            val exercise = repository.getExerciseById(id)
            _exercise.value = exercise
        }
    }

    fun createExercise(exercise: Exercise) {
        viewModelScope.launch {
            val isSuccessful = repository.createExercise(exercise)
            if (isSuccessful) {
                getAllExercises()
            }
        }
    }

    fun deleteExerciseById(id: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val isSuccessful = repository.deleteExerciseById(id)
            if (isSuccessful) {
                getAllExercises()
            }
            onSuccess()
        }
    }
}