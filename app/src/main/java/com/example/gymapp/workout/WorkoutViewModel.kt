package com.example.gymapp.workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.models.AddExerciseToWorkout
import com.example.gymapp.models.ExerciseWorkouts
import com.example.gymapp.models.Workout
import com.example.gymapp.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val repository: MyRepository
) : ViewModel() {
    private val _workouts = MutableLiveData<List<Workout>>(emptyList())
    open val workouts: LiveData<List<Workout>> = _workouts
    private val _workout = MutableLiveData<Workout>(null)
    open val workout: LiveData<Workout> = _workout
    private val _exerciseWorkouts = MutableLiveData<List<ExerciseWorkouts>?>(null)
    private val _exerciseWorkout = MutableLiveData<ExerciseWorkouts>(null)
    open val exerciseWorkout: LiveData<ExerciseWorkouts> = _exerciseWorkout
    open val exerciseWorkouts: MutableLiveData<List<ExerciseWorkouts>?> = _exerciseWorkouts
    private val _workoutFrom = MutableLiveData<Workout>()
    open val workoutFrom: LiveData<Workout> = _workoutFrom

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

    fun updateExerciseWorkout(updatedExerciseWorkout: ExerciseWorkouts, workoutId: Long) {
        viewModelScope.launch {
            val result = repository.updateExerciseWorkoutById(
                updatedExerciseWorkout.id,
                updatedExerciseWorkout
            )
            if (result) {
                getWorkoutById(workoutId)
            }
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

    fun getAllExerciseWorkouts() {
        viewModelScope.launch {
            _exerciseWorkouts.postValue(repository.getAllExerciseWorkout())
        }
    }

    private suspend fun createExerciseWorkout(exerciseWorkouts: ExerciseWorkouts): ExerciseWorkouts {
        return withContext(Dispatchers.IO) {
            repository.createExerciseWorkout(exerciseWorkouts)
        }
    }

    private fun addExerciseToWorkout(addExerciseToWorkout: AddExerciseToWorkout) {
        viewModelScope.launch {
            val isSuccessful = repository.addExerciseToWorkout(addExerciseToWorkout)
            if (isSuccessful) {
                getAllWorkouts()
            }
        }
    }

    fun createExerciseWorkoutAndAddToWorkout(exerciseWorkout: ExerciseWorkouts, workoutId: Long) {
        viewModelScope.launch {
            val exerciseWorkoutCreated = createExerciseWorkout(exerciseWorkout)
            addExerciseToWorkout(AddExerciseToWorkout(exerciseWorkoutCreated.id, workoutId))
        }
    }
}
