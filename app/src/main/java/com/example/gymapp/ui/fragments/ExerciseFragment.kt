package com.example.gymapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.gymapp.ui.screens.exercise.ExerciseRoomViewModel
import com.example.gymapp.ui.screens.exercise.ExerciseScreen
import com.example.gymapp.ui.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseFragment : Fragment() {
    private val exerciseRoomViewModel: ExerciseRoomViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        //exerciseRoomViewModel.getSavedExercises()
//        exerciseRoomViewModel.insertExercisesToDatabase()
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    ExerciseScreen(viewModel = exerciseRoomViewModel, onExerciseClick = {
                        exerciseRoomViewModel.amogus()
                    })
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exerciseRoomViewModel.amogus()
    }
}

