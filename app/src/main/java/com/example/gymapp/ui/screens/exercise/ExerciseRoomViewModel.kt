package com.example.gymapp.ui.screens.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.data.repositories.MyRepository
import com.example.gymapp.domain.exercises.ExerciseService
import com.example.gymapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseRoomViewModel @Inject constructor(
    private val apiRepository: MyRepository,
    private val exerciseService: ExerciseService
) : ViewModel() {
    private val _exercises: MutableLiveData<Resource<List<Exercise>>> =
        MutableLiveData(Resource.Loading())
    val exercises: LiveData<Resource<List<Exercise>>> = _exercises
    private val _exercise: MutableLiveData<Resource<Exercise>> = MutableLiveData(Resource.Loading())
    val exercise: LiveData<Resource<Exercise>> = _exercise
    init {
        viewModelScope.launch { exerciseService.amogus() }
//        insertExercisesToDatabase()
//        getSavedExercises()
    }
//    fun insertExercisesToDatabase() {
//        viewModelScope.launch {
//            try {
//                val response = apiRepository.getAllExercises()
//                if (response.isSuccessful) {
//                    val exercises = response.body()
//                    if (exercises != null) {
//                        exerciseService.insertExercises(exercises)
//                        _exercises.postValue(Resource.Success(exercises))
//                    } else {
//                        _exercises.postValue(Resource.Error("Empty exercise list"))
//                    }
//                } else {
//                    _exercises.postValue(Resource.Error("Failed to fetch exercises"))
//                }
//            } catch (e: Exception) {
//                _exercises.postValue(Resource.Error("Failed to fetch exercises"))
//            }
//        }
//    }
//
//    fun insertExercise(exercise: Exercise) = viewModelScope.launch {
//        _exercise.postValue(Resource.Loading())
//        try {
//            exerciseService.insertExercise(exercise)
//            _exercise.postValue(Resource.Success(exercise))
//        } catch (e: Exception) {
//            _exercise.postValue(Resource.Error("Failed to add exercise"))
//        }
//    }
//
//    fun getSavedExercises() {
//        viewModelScope.launch {
//            exerciseService.getAllExercises().collect {
//
//            }
//        }
//    }
}