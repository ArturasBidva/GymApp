package com.example.gymapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.gymapp.workout.StartWorkoutFragmentDirections
import com.example.gymapp.workout.StartWorkoutScreen
import com.example.gymapp.workout.WorkoutViewModel

class StartWorkoutFragment : Fragment() {
    private val workoutViewModel: WorkoutViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                StartWorkoutScreen(workoutViewModel = workoutViewModel, onConfirmClick = {
                    val action = StartWorkoutFragmentDirections.actionStartWorkoutFragmentToOnGoingWorkoutFragment(it)
                    findNavController().navigate(action)
                })
            }
        }
    }
}