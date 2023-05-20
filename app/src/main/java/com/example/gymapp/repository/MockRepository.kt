import com.example.gymapp.models.Exercise
import com.example.gymapp.models.ExerciseCategory
import com.example.gymapp.repository.MyRepository // Replace with the correct import path for your repository

class MockRepository : MyRepository {
    override suspend fun getAllExercises(): List<Exercise> {
        return listOf(
            Exercise(
                id = 1,
                title = "Bench Press",
                weight = 100,
                imgUrl = "https://example.com/image.png",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                listOf()
            ),
        )
    }

    override suspend fun updateExercise(id: Long, exercise: Exercise): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun createExercise(exercise: Exercise): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getExerciseById(id: Long): Exercise {
        TODO("Not yet implemented")
    }

    override suspend fun deleteExerciseById(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCategories(): List<ExerciseCategory> {
        TODO("Not yet implemented")
    }
}