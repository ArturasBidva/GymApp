package com.example.gymapp.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController

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