package com.example.gymapp.profile

import androidx.lifecycle.ViewModel
import com.example.gymapp.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: MyRepository

) : ViewModel() {

}

