package com.example.gymapp.ui.screens.workoutschedule

sealed class TimeSelectionDialogType {
    data object StartTime: TimeSelectionDialogType()
    data object EndTime: TimeSelectionDialogType()
    data object StartTimeUpdate: TimeSelectionDialogType()
    data object EndTimeUpdate: TimeSelectionDialogType()

}
