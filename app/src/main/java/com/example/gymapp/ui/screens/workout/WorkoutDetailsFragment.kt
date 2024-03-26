package com.example.gymapp.ui.screens.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class WorkoutDetailsFragment : Fragment() {
    private val workoutViewModel: WorkoutViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val workoutInfoState = workoutViewModel.uiState.collectAsState()
                workoutInfoState.value.selectedWorkout?.let { WorkoutDetailsScreen(workout = it) }
            }
        }
    }
}