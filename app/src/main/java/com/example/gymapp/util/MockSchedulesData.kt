import com.example.gymapp.data.db.models.local.Schedule
import com.example.gymapp.data.db.models.local.WorkoutLocal
import java.time.LocalDate
import java.time.LocalTime

object MockSchedulesData {
    private val mockWorkoutLocal = WorkoutLocal(
        id = 1,
        title = "Sample Workout",
        description = "This is a sample workout"
    )

    val mockSchedules: List<Schedule> = listOf(
        Schedule(
            id = 1,
            workout = mockWorkoutLocal,
            date = LocalDate.now(),
            startTime = LocalTime.of(9, 0),
            endTime = LocalTime.of(10, 0),
            color = 0xFFAABBCC.toInt() // Example color
        ),
        Schedule(
            id = 2,
            workout = mockWorkoutLocal,
            date = LocalDate.now().plusDays(1),
            startTime = LocalTime.of(10, 0),
            endTime = LocalTime.of(11, 0),
            color = 0xFFDDCCBB.toInt()
        )
    )
}