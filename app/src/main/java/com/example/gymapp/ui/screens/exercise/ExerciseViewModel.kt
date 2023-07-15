package com.example.gymapp.ui.screens.exercise

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.data.repositories.MyRepository
import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.domain.exercises.ExerciseEvent
import com.example.gymapp.domain.exercises.ExerciseService
import com.example.gymapp.domain.exercises.ExerciseState
import com.example.gymapp.util.Resource
import com.example.gymapp.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val repository: MyRepository,
    private val exerciseService: ExerciseService
) : ViewModel() {
    private val _exercise = MutableLiveData<Exercise>(null)
    val exercise: LiveData<Exercise> = _exercise
    private val _exercises: MutableStateFlow<Resource<List<Exercise>>> =
        MutableStateFlow(Resource.Loading())
    val exercises: StateFlow<Resource<List<Exercise>>> = _exercises
    private val _eventFlow = MutableSharedFlow<UiText?>()
    val eventFlow: SharedFlow<UiText?> = _eventFlow
    private val _uiState = MutableStateFlow(ExerciseState())
    val uiState = combine(_uiState, exercises) { state, exercises ->
        state.copy(exercise = exercises)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExerciseState())

    init {
            getExercises()
    }

    fun onEvent(event: ExerciseEvent, onFetchComplete: () -> Unit = {}) {
        when (event) {
            is ExerciseEvent.DeleteExercise -> {
                viewModelScope.launch {
                    val result = exerciseService.deleteExerciseById(exerciseId = event.exercise.id)
                    _eventFlow.emit(
                        when (result) {
                            is Resource.Success -> {
                                Log.d("DebugTag", "Emitting success message")
                                UiText.DynamicString("Exercise deleted successfully")
                            }

                            is Resource.Error -> {
                                result.message
                            }

                            is Resource.Empty -> {
                                UiText.DynamicString("Empty")
                            }

                            is Resource.Loading -> {
                                UiText.DynamicString("Loading")
                            }
                        }
                    )
                    if (result is Resource.Success) {
                   getExercises {
                       onFetchComplete()
                   }
                    }
                }
            }

            is ExerciseEvent.SetDescription -> {
                _uiState.update { it.copy(description = event.description) }
            }

            is ExerciseEvent.SetImgUrl -> {
                _uiState.update { it.copy(imgUrl = event.imgUrl) }
            }

            is ExerciseEvent.SaveExercise -> {
                val title = uiState.value.title
                val imgUrl = uiState.value.imgUrl
                val description = uiState.value.description
                val category = uiState.value.category
                if (title.isBlank() || imgUrl.isBlank() || description.isBlank()) {
                    viewModelScope.launch {
                        _eventFlow.emit(UiText.DynamicString("Exercise can't be empty"))
                    }
                    return
                }
                val exercise = Exercise(
                    id = 0,
                    title = title,
                    0,
                    description = description,
                    category = category,
                    imgUrl = imgUrl
                )
                viewModelScope.launch {
                    val result = repository.createExercise(exercise)
                    getExercises()
                    _eventFlow.emit(
                        when (result) {
                            is Resource.Success -> {
                                UiText.DynamicString("Exercise created successfully")
                            }

                            is Resource.Error -> {
                                result.message
                            }

                            is Resource.Empty -> {
                                UiText.DynamicString("Empty")
                            }

                            is Resource.Loading -> {
                                UiText.DynamicString("Loading")
                            }
                        }
                    )
                }
                _uiState.update {
                    it.copy(
                        title = "",
                        description = "",
                        imgUrl = "",
                        category = emptyList(),
                    )
                }
            }

            is ExerciseEvent.SetCategory -> {
                _uiState.update {
                    it.copy(
                        category = event.category
                    )
                }
            }

            is ExerciseEvent.SetTitle -> {
                _uiState.update {
                    it.copy(
                        title = event.title
                    )
                }
            }
        }
    }

    private fun getExercises(onFetchComplete: () -> Unit = {}) {
        viewModelScope.launch {
            exerciseService.getExercises()
                .onStart {
                    _exercises.update { Resource.Loading() }}
                .collect { items ->
                    _exercises.update { items }
                    onFetchComplete()
                }
        }
    }

    fun updateExercise(id: Long, exercise: Exercise) {
        viewModelScope.launch {
            val isSuccessful = repository.updateExercise(id, exercise)
            if (isSuccessful) {
                getExercises()
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
}