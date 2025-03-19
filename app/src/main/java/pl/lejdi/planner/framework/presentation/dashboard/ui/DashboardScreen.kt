package pl.lejdi.planner.framework.presentation.dashboard.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.Serializable
import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.framework.presentation.common.navigation.FAB_EXPLODE_ANIMATION_KEY
import pl.lejdi.planner.framework.presentation.common.ui.BaseScreen
import pl.lejdi.planner.framework.presentation.dashboard.DashboardContract
import pl.lejdi.planner.framework.presentation.dashboard.DashboardViewModel
import pl.lejdi.planner.framework.presentation.dashboard.ui.DashboardScreenTestTags.DASHBOARD_FAB_TEST_TAG
import pl.lejdi.planner.framework.presentation.dashboard.ui.DashboardScreenTestTags.DASHBOARD_TASKS_PAGER_TAG

@Serializable
object DashboardScreenRoute

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DashboardScreen(
    navigateToDetails: (Task?) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel(),
    animatedVisibilityScope: AnimatedVisibilityScope
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
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.sendEvent(DashboardContract.Event.AddButtonClicked) },
                modifier = Modifier
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState(FAB_EXPLODE_ANIMATION_KEY),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(500)
                        }
                    )
                    .testTag(DASHBOARD_FAB_TEST_TAG)
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
                    .testTag(DASHBOARD_TASKS_PAGER_TAG)
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

object DashboardScreenTestTags{
    const val DASHBOARD_FAB_TEST_TAG = "Dashboard.FAB"
    const val DASHBOARD_TASKS_PAGER_TAG = "Dashboard.TasksPager"
}