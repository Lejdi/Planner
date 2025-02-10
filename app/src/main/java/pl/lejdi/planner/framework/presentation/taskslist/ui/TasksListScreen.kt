package pl.lejdi.planner.framework.presentation.taskslist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.Serializable
import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.framework.presentation.taskslist.TasksListContract
import pl.lejdi.planner.framework.presentation.taskslist.TasksListViewModel

@Serializable
object TasksListScreenRoute

@Composable
fun TasksListScreen(
    navigateToDetails: (Task) -> Unit,
    viewModel: TasksListViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel.effect) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is TasksListContract.Effect.NavigateToDetails -> {
                    navigateToDetails(effect.task)
                }
            }
        }.collect()
    }
    Column {
        viewModel.viewState.value.tasksList.forEach { task ->
            Button(
                onClick = {
                    viewModel.sendEvent(TasksListContract.Event.TaskClicked(task))
                },
                content = {
                    Text(task.name)
                }
            )
        }
    }
}