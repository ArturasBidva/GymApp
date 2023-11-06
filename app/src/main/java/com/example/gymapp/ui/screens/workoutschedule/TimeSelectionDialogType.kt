package com.example.gymapp.ui.screens.workoutschedule

sealed class TimeSelectionDialogType {
    object StartTime: TimeSelectionDialogType()
    object EndTime: TimeSelectionDialogType()

}
