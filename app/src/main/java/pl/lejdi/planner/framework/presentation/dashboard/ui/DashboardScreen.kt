package pl.lejdi.planner.framework.presentation.dashboard.ui

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
import pl.lejdi.planner.framework.presentation.dashboard.DashboardContract
import pl.lejdi.planner.framework.presentation.dashboard.DashboardViewModel

@Serializable
object DashboardScreenRoute

@Composable
fun DashboardScreen(
    navigateToDetails: (Task?) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel.effect) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is DashboardContract.Effect.NavigateToDetails -> {
                    navigateToDetails(effect.task)
                }
            }
        }.collect()
    }
    Column {
        viewModel.viewState.value.daysTasksMap.forEach { (_, taskList) ->
            taskList.forEach { task ->
                Button(
                    onClick = {
                        viewModel.sendEvent(DashboardContract.Event.TaskClicked(task))
                    },
                    content = {
                        Text(task.name)
                    }
                )
            }

        }
    }
}