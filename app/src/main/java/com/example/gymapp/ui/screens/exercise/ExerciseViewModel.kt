package com.example.gymapp.ui.screens.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.data.repositories.local.exercise.DataExerciseService
import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.domain.exercises.ExerciseService
import com.example.gymapp.util.Resource
import com.example.gymapp.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val dataExerciseService: DataExerciseService,
    private val domainExerciseService: ExerciseService
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExerciseUiState())
    val uiState: StateFlow<ExerciseUiState> = _uiState

    init {
        observeUiEvent()
        getExercises()
        getExerciseCategories()
    }

    private fun observeUiEvent() {
        viewModelScope.launch {
            uiState.collect { uiState ->
                when (uiState.uiEvent) {
                    is ExerciseUiEvent.SelectExerciseCategory -> {
                        _uiState.update {
                            it.copy(
                                selectedExerciseCategory = uiState.uiEvent.exerciseCategory
                            )
                        }
                    }

                    is ExerciseUiEvent.SelectExercise -> {
                        _uiState.update {
                            it.copy(selectedExercise = uiState.uiEvent.exercise)
                        }
                    }

                    is ExerciseUiEvent.DeleteExercise -> {
                        val result = deleteExercise(uiState.uiEvent.exercise)
                        _uiState.update {
                            it.copy(
                                uiText = when (result) {
                                    is Resource.Success -> {
                                        domainExerciseService.syncDataWithAPI()
                                        UiText.DynamicString("Exercise deleted successfully")

                                    }

                                    is Resource.Empty -> result.message
                                    is Resource.Error -> UiText.DynamicString("Empty")
                                    is Resource.Loading -> UiText.DynamicString("Loading")
                                }
                            )
                        }
                    }

                    is ExerciseUiEvent.DeleteExerciseSuccess -> {
                        //do nothing
                    }
                    ExerciseUiEvent.CloseAddExerciseToWorkoutDialog -> {
                        _uiState.update { it.copy(selectedExercise = null) }
                    }

                    null -> {
                        //do nothing
                    }

                }
            }
        }
    }

    fun onSelectionClick() {
        _uiState.update { it.copy(selectionExpanded = !it.selectionExpanded) }
    }

    fun onEvent(event: ExerciseUiEvent?) {
        _uiState.update { it.copy(uiEvent = event) }
    }

    private fun getExerciseCategories() {
        viewModelScope.launch {
            dataExerciseService.getExerciseCategories()
                .onStart {
                    _uiState.update { it.copy(isLoading = true) }
                }.catch {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }.collect { items ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            exerciseCategories = items
                        )
                    }
                }
        }
    }

    private fun getExercises() {
        viewModelScope.launch {
            dataExerciseService.getExercisesWithCategories()
                .onStart {
                    _uiState.update { it.copy(isLoading = true) }
                }
                .catch {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }
                .collect { items ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            exercises = items
                        )
                    }
                }
        }
    }

    private fun deleteExercise(exercise: Exercise): Resource<Unit> {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            onEvent(ExerciseUiEvent.DeleteExerciseSuccess)
            when (domainExerciseService.deleteExercise(exercise)) {
                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                }

                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                }

                else -> {
                    _uiState.update { it.copy(isLoading = false) }
                    // Do nothing
                }
            }
        }

        return Resource.Success(Unit)
    }
}