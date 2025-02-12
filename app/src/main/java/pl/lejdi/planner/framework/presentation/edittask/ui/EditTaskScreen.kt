package pl.lejdi.planner.framework.presentation.edittask.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.serialization.Serializable
import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.framework.presentation.edittask.EditTaskViewModel

@Serializable
data class EditTaskScreenRoute(
    val taskDetails: Task?
)

@Composable
fun EditTaskScreen(
    taskDetails: Task?,
    navigateBack: () -> Unit,
    viewModel: EditTaskViewModel = hiltViewModel()
) {
    Column {
        taskDetails?.let{
            Text(it.name)
            Text(it.startDate.toString())
        }
        Button( onClick =  {
            navigateBack()
        },
            content = {
                Text("click me!")
            })
    }
}