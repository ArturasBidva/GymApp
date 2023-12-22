package com.example.gymapp.ui.screens.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.data.db.models.local.WorkoutLocal
import com.example.gymapp.domain.workouts.ExerciseWorkout
import com.example.gymapp.domain.workouts.Workout
import com.example.gymapp.domain.workouts.DomainWorkoutService
import com.example.gymapp.util.Resource
import com.example.gymapp.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val domainWorkoutService: DomainWorkoutService
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkoutUiState())
    val uiState: StateFlow<WorkoutUiState> = _uiState

    init {
        getAllWorkouts()
        observeUiEvent()
    }

    private fun observeUiEvent() {
        viewModelScope.launch {
            uiState.collect { uiState ->
                when (uiState.uiEvent) {
                    is WorkoutUiEvent.SaveWorkout -> {
                        val result = createWorkout(workout = uiState.uiEvent.workout)
                        _uiState.update { it ->
                            it.copy(
                                uiText = when (result) {
                                    is Resource.Success -> {
                                        _uiState.update { it.copy(isCreated = true) }
                                        getAllWorkouts()
                                        onEvent(null)
                                        UiText.DynamicString("Workout saved successfully")
                                    }

                                    is Resource.Empty -> UiText.DynamicString("Empty")
                                    is Resource.Error -> result.message
                                    is Resource.Loading -> UiText.DynamicString("Loading")
                                }
                            )
                        }
                    }

                    is WorkoutUiEvent.DeleteWorkout -> {
                        val result = deleteWorkoutById(workoutId = uiState.uiEvent.workoutId)
                        _uiState.update {
                            it.copy(
                                uiText = when (result) {
                                    is Resource.Success -> {
                                        getAllWorkouts { onEvent(WorkoutUiEvent.DeleteWorkoutSuccess) }
                                        UiText.DynamicString("Workout deleted successfully")
                                    }

                                    is Resource.Error -> result.message
                                    is Resource.Empty -> UiText.DynamicString("Empty")
                                    is Resource.Loading -> UiText.DynamicString("Loading")
                                }
                            )
                        }
                    }

                    is WorkoutUiEvent.DeleteWorkoutSuccess -> {
                        // Do nothing
                    }

                    is WorkoutUiEvent.DismissWorkoutDialog -> {
                        _uiState.update { it.copy(workoutInfo = null) }
                    }

                    is WorkoutUiEvent.SelectWorkout -> {
                        _uiState.update { it.copy(selectedWorkout = uiState.uiEvent.workout) }
                    }


                    is WorkoutUiEvent.SaveWorkoutSuccess -> {

                    }

                    is WorkoutUiEvent.DismissAddExerciseToWorkoutDialog -> {
                    _uiState.update { it.copy(isCreated = false) }

                    }

                    is WorkoutUiEvent.CreateExerciseWorkout -> {
                        addExerciseToWorkout(
                            exerciseWorkout = uiState.uiEvent.exerciseWorkout,
                            workout = uiState.uiEvent.workout
                        )
                        _uiState.update {
                            it.copy(
                                selectedWorkout = uiState.uiEvent.workout,
                                isCreated = true,
                            )
                        }



                        onEvent(null)
                        _uiState.update { it.copy(isCreated = false) }
                    }

                    is WorkoutUiEvent.DeleteExerciseWorkoutFromWorkout -> {
                        deleteExerciseWorkoutFromWorkout(
                            uiState.uiEvent.workoutId,
                            uiState.uiEvent.exerciseId
                        )
                        onEvent(null)
                    }

                    null -> {
                        //do nothing
                    }

                }
            }
        }
    }

    private fun deleteExerciseWorkoutFromWorkout(workoutId: Long, exerciseId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val response = domainWorkoutService.deleteExerciseWorkoutFromWorkoutById(
                workoutId = workoutId,
                exerciseId = exerciseId
            )
            if (response is Resource.Success) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }


    private fun getAllWorkouts(onFetchComplete: () -> Unit = {}) {
        viewModelScope.launch {
            domainWorkoutService.getWorkouts()
                .onStart {
                    _uiState.update { it.copy(isLoading = true) }
                }
                .onEach { WorkoutUiState(isLoading = true) }
                .catch {
                    _uiState.update { it.copy(isLoading = false) }
                }
                .collect { items ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            workouts = items,
                            selectedWorkout = if (items.isNotEmpty()) items.first() else null
                        )
                    }
                    onFetchComplete()
                }
        }
    }

    private fun createWorkout(
        workout: Workout,
        onUiEvent: (WorkoutUiEvent) -> Unit = {}
    ): Resource<Workout> {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val result = domainWorkoutService.createWorkout(workout = workout)
                if (result is Resource.Success) {
                    _uiState.update { it.copy(isLoading = false, workout = result.data) }
                    onUiEvent(WorkoutUiEvent.SaveWorkoutSuccess)
                } else {
                    _uiState.update { it.copy(isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }

        return Resource.Success(workout)
    }

    private suspend fun deleteWorkoutById(workoutId: Long): Resource<Unit> {
        _uiState.update { it.copy(isLoading = true) }
        return try {
            domainWorkoutService.deleteWorkoutById(workoutId = workoutId)
            Resource.Success(null)
        } catch (e: Exception) {
            Resource.Error(UiText.DynamicString(e.message.toString()))
        } finally {
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onEvent(event: WorkoutUiEvent?) {
        _uiState.update { it.copy(uiEvent = event) }
    }

    private fun addExerciseToWorkout(
        exerciseWorkout: ExerciseWorkout,
        workout: WorkoutLocal
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            domainWorkoutService.addExerciseWorkoutToWorkout(workout, exerciseWorkout)
        }
    }
}