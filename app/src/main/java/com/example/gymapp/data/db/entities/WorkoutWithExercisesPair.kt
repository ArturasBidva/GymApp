import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.gymapp.data.db.entities.ExerciseEntity
import com.example.gymapp.data.db.entities.WorkoutEntity

data class WorkoutWithExercises(
    @Embedded val workout: WorkoutEntity,
    @Relation(
        parentColumn = "workoutId",
        entityColumn = "exerciseId",
        associateBy = Junction(WorkoutExerciseCrossRef::class)
    )
    val exercises: List<ExerciseEntity>
)