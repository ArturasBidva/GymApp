package com.example.gymapp.ui.screens.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.data.repositories.local.workout.DataWorkoutService
import com.example.gymapp.domain.exercises.ExerciseService
import com.example.gymapp.domain.workouts.DomainWorkoutService
import com.example.gymapp.ui.screens.workout.WorkoutViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val exerciseService: ExerciseService,
    private val domainWorkoutService: DomainWorkoutService,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainScreenUiState> =
        MutableStateFlow(MainScreenUiState())
    val uiState: StateFlow<MainScreenUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            exerciseService.syncDataWithAPI()
            domainWorkoutService.syncWorkoutDataWithAPI()
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}