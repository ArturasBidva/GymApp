package com.example.gymapp.ui.screens.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.gymapp.ui.screens.exercise.ExerciseViewModel
import com.example.gymapp.ui.screens.workout.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreenFragment : Fragment() {
    private val workoutViewModel: WorkoutViewModel by activityViewModels()
    private val exerciseViewModel: ExerciseViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MainScreen(
                    navigateToWorkout = {
                        val action =
                            MainScreenFragmentDirections.actionMainScreenFragmentToWorkoutsFragment()
                        findNavController().navigate(action)
                    }, navigateToExercise = {
                        val action =
                            MainScreenFragmentDirections.actionMainScreenFragmentToExerciseScreen()
                        findNavController().navigate(action)
                    }
                    , navigateToWorkoutSchedule = {
                        val action =
                            MainScreenFragmentDirections.actionMainScreenFragmentToWorkoutSchedule()
                        findNavController().navigate(action)
                    }
                )
            }
        }
    }
}