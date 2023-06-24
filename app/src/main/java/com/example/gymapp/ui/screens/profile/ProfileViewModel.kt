package com.example.gymapp.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.example.gymapp.data.repositories.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: MyRepository

) : ViewModel() {

}

