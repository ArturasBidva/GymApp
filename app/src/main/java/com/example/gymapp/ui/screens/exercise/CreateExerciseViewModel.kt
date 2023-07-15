package com.example.gymapp.ui.screens.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.data.repositories.MyRepository
import com.example.gymapp.domain.exercises.ExerciseCategory
import com.example.gymapp.domain.exercises.ExerciseService
import com.example.gymapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateExerciseViewModel @Inject constructor(
    private val repository: MyRepository,
    private val exerciseService: ExerciseService
) : ViewModel() {
    private val _exerciseCategories: MutableStateFlow<Resource<List<ExerciseCategory>>> =
        MutableStateFlow(Resource.Loading())
    val exerciseCategories: StateFlow<Resource<List<ExerciseCategory>>> = _exerciseCategories

    init {
        getAllExerciseCategories()
    }

    private fun getAllExerciseCategories() {
        viewModelScope.launch {
            exerciseService.getAllExerciseCategories()
                .onStart {
                    _exerciseCategories.update {
                        Resource.Loading()
                    }
                }.collect { items ->
                    _exerciseCategories.update { items }
                }
        }
    }
}