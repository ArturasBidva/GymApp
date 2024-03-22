package com.example.gymapp.ui.screens.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

class WorkoutFragment : Fragment() {

    private val workoutViewModel: WorkoutViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                WorkoutScreen(
                    workoutViewModel = workoutViewModel,
                    onBackClick = { findNavController().popBackStack() },
                    onCreateWorkoutClick = {
                        val action =
                            WorkoutFragmentDirections.actionWorkoutFragmentToWorkoutCreateWorkoutFragment()
                        findNavController().navigate(action)
                    },
                    onClickSeeMore = {
                        val action =
                            WorkoutFragmentDirections.actionWorkoutFragmentToWorkoutDetailsFragment()
                        findNavController().navigate(action)
                    }
                )
            }
        }
    }
}