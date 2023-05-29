package com.example.gymapp.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gymapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MenuScreen(
                    onCreateExerciseClick = { findNavController().navigate(R.id.action_menuFragment_to_createExerciseFragment) },
                    onYourWorkoutsClick = { findNavController().navigate(R.id.action_menuFragment_to_WorkoutsDetailsFragment) },
                    onCreateWorkoutClick = { findNavController().navigate(R.id.action_menuFragment_to_CreateWorkoutFragment) })
            }
        }
    }
}
