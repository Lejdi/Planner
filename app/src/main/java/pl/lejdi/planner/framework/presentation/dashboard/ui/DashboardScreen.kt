package pl.lejdi.planner.framework.presentation.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.Serializable
import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.framework.presentation.common.ui.BaseScreen
import pl.lejdi.planner.framework.presentation.dashboard.DashboardContract
import pl.lejdi.planner.framework.presentation.dashboard.DashboardViewModel

@Serializable
object DashboardScreenRoute

@Composable
fun DashboardScreen(
    navigateToDetails: (Task?) -> Unit,
    refreshScreen: Boolean,
    viewModel: DashboardViewModel = hiltViewModel()
) = BaseScreen(
    displayProgressBar = viewModel.viewState.value.isLoading,
    errorsQueue = viewModel.errorsQueue
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
    LaunchedEffect(refreshScreen) {
        if(refreshScreen) {
            viewModel.sendEvent(DashboardContract.Event.RefreshTasks)
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.sendEvent(DashboardContract.Event.AddButtonClicked) },
            ) {
                Icon(Icons.Filled.Add, null)
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background
                )
        ) {
            val pagerState = rememberPagerState { viewModel.viewState.value.daysTasksMap.size }
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 32.dp),
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .padding(contentPadding)
            ) { page ->
                val (date, tasksList) = viewModel.viewState.value.daysTasksMap[page]
                SingleDayView(
                    date = date,
                    tasks = tasksList,
                    onEditClick = { clickedTask ->
                        viewModel.sendEvent(DashboardContract.Event.EditButtonClicked(clickedTask))
                    },
                    onCompleteClick = { clickedTask ->
                        viewModel.sendEvent(DashboardContract.Event.CompleteButtonClicked(clickedTask))
                    },
                    selectedPage = pagerState.currentPage
                )
            }
        }
    }
}