package com.example.gymapp.ui.screens.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.domain.exercises.ExerciseCategory
import com.example.gymapp.data.repositories.MyRepository
import com.example.gymapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val repository: MyRepository
) : ViewModel() {
    private val _exercise = MutableLiveData<Exercise>(null)
    val exercise: LiveData<Exercise> = _exercise
    private val _exercises: MutableLiveData<Resource<List<Exercise>>> = MutableLiveData(Resource.Loading())
    val exercises: LiveData<Resource<List<Exercise>>> = _exercises
    private val _exerciseCategories = MutableLiveData<List<ExerciseCategory>>(emptyList())
    val exerciseCategories: LiveData<List<ExerciseCategory>> = _exerciseCategories

    init {
        getAllExercises()
        getAllExerciseCategories()
    }

    private fun getAllExercises() = viewModelScope.launch {
        _exercises.postValue(Resource.Loading())
        val response = repository.getAllExercises()
        _exercises.postValue(handleGetExercisesResponse(response))

    }

    private fun getAllExerciseCategories() {
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

    private fun handleGetExercisesResponse(response: Response<List<Exercise>>): Resource<List<Exercise>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}