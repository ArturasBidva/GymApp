package com.example.gymapp.ui.screens.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.R
import com.example.gymapp.data.db.models.local.WorkoutLocal
import com.example.gymapp.domain.workouts.Workout
import com.example.gymapp.ui.customOrange
import com.example.gymapp.ui.montserrati
import com.example.gymapp.ui.quicksandBold
import com.example.gymapp.ui.quicksandMedium
import com.example.gymapp.ui.reusable.LoadingCircle
import com.example.gymapp.ui.screens.mainscreen.TestHeader
import com.example.gymapp.util.MockWorkoutData
import com.example.gymapp.util.MockWorkoutLocalData

@Composable
fun WorkoutScreen(
    workoutViewModel: WorkoutViewModel,
    onBackClick: () -> Unit,
    onCreateWorkoutClick: () -> Unit,
    onClickSeeMore: () -> Unit
) {
    val workoutUiState by workoutViewModel.uiState.collectAsState()

    if (workoutUiState.isLoading) {
        LoadingCircle()
    } else {
        Content(
            workoutUiState = workoutUiState,
            onWorkoutUiEvent = workoutViewModel::onEvent,
            onBackClick = onBackClick,
            onCreateWorkoutClick = onCreateWorkoutClick,
            onClickSeeMore = onClickSeeMore
        )
    }
}

@Composable
private fun Content(
    workoutUiState: WorkoutUiState,
    onWorkoutUiEvent: (WorkoutUiEvent) -> Unit,
    onBackClick: () -> Unit,
    onCreateWorkoutClick: () -> Unit,
    onClickSeeMore: () -> Unit
) {
    MaterialTheme {
//        workoutUiState.workoutInfo?.let {
//            WorkoutDetailsScreen(
//                workout = it,
//              onDismiss = {
//                  onWorkoutUiEvent(WorkoutUiEvent.DismissWorkoutDialog)
//              }
//            )
//        }
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                TestHeader(text = "Workouts")
                Spacer(modifier = Modifier.height(15.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    workoutUiState.workouts.forEach { workout ->
                        WorkoutCard(
                            workout = workout,
                            onDeleteClick = {
                                onWorkoutUiEvent(WorkoutUiEvent.DeleteWorkout(workout.id))
                            },
                            onClickSeeMore = {
                                onWorkoutUiEvent(WorkoutUiEvent.SelectWorkout(workout))
                                onClickSeeMore()
                            }
                        )
                    }
                }
            }
        }
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 60.dp, horizontal = 8.dp)
        ) {
            CreateButton(onCreateClick = onCreateWorkoutClick, text = "Create workout")
        }
    }
}

@Composable
fun CreateButton(onCreateClick: () -> Unit, text: String) {
    Box(modifier = Modifier.clickable { onCreateClick() }) {
        Box(
            modifier = Modifier
                .shadow(1.dp, shape = RoundedCornerShape(45))
                .background(
                    color = Color(0xFFE5E6E9),
                    shape = RoundedCornerShape(45)
                )
                .padding(6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(color = Color(0xFF2B2E4A), shape = CircleShape)
                ) {
                    Text(
                        text = "+",
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = text,
                    color = Color(0xFF2B2E4A),
                    fontFamily = montserrati
                )
                Spacer(modifier = Modifier.width(9.dp))
            }
        }
    }
}

@Composable
fun WorkoutCard(
    workout: WorkoutLocal,
    onClickSeeMore: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.deadlift),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 18.dp)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Workout",
                color = customOrange,
                fontFamily = quicksandBold,
                fontSize = 12.sp
            )
            Text(
                text = workout.title,
                color = Color.White,
                fontFamily = quicksandBold,
                fontSize = 20.sp
            )
            Text(
                text = workout.description,
                color = Color.White,
                fontFamily = quicksandMedium,
                fontSize = 14.sp,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(50.dp))
            Row()
            {
                RemoveButtonIcon { onDeleteClick() }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "Remove workout",
                    color = Color(0xFFB94C1D),
                    modifier = Modifier.clickable { onDeleteClick() }
                )
                Spacer(modifier = Modifier.width(30.dp))
                SeeMoreButton(onClickSeeMore)
            }
        }
    }
}

@Composable
fun RemoveButtonIcon(onDeleteClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(Color(0xFFB94C1D))
            .clickable { onDeleteClick() }
    ) {
        Text(
            text = "-",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun AddButtonIcon(onAddClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(Color(0xFFFFAB40))
            .clickable { onAddClick() }
    ) {
        Text(text = "+",
            modifier = Modifier
                .align(Alignment.Center)
                .clickable { onAddClick() }
        )
    }
}

@Composable
fun SeeMoreButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .shadow(1.dp, shape = RoundedCornerShape(45))
            .width(70.dp)
            .height(24.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = customOrange)
            .clickable { onClick() }
    ) {
        Text(
            text = "See more",
            fontFamily = quicksandBold,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center).clickable { onClick() }
        )
    }
}

@Preview(group = "amogus")
@Composable
fun RemoveButtonIconPreview() {
    RemoveButtonIcon({})
}

@Preview
@Composable
fun SeeMoreButtonPrev() {
    SeeMoreButton({})
}

@Preview(group = "amogus")
@Composable
fun WorkoutCardTestPreview() {
    WorkoutCard(workout = MockWorkoutLocalData.mockWorkoutsLocal.first(), {}, {})
}

@Preview
@Composable
fun WorkoutScreenPrev() {
    Content(
        workoutUiState = WorkoutUiState(),
        onBackClick = {},
        onWorkoutUiEvent = {},
        onCreateWorkoutClick = {},
        onClickSeeMore = {}
    )
}

@Preview(group = "Amoguseris")
@Composable
fun CreateWorkoutButtonPreview() {
    CreateButton({},"kazkas")
}
