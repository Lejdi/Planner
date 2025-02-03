package pl.lejdi.planner.framework.presentation.taskslist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import pl.lejdi.planner.framework.presentation.taskslist.TasksListContract
import kotlinx.coroutines.flow.collect

@Composable
fun TasksListScreen(
    state: TasksListContract.State,
    effectFlow: Flow<TasksListContract.Effect>?,
    onEventSent: (TasksListContract.Event) -> Unit,
    onNavigationRequested: (String) -> Unit
) {
    LaunchedEffect(effectFlow) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is TasksListContract.Effect.NavigateToDetails -> {
                    onNavigationRequested("1")
                }
            }
        }?.collect()
    }
    Box {
        Button(
            onClick = {
                onEventSent(TasksListContract.Event.TaskClicked("1"))
            },
            content = {
                state.tasksList.forEach {
                    Text("lel")
                }
            }
        )
    }
}