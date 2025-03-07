package pl.lejdi.planner.framework.presentation.dashboard.ui

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayable

@Composable
fun SingleDayView(
    date: String,
    tasks: List<TaskDisplayable>,
    onEditClick: (TaskDisplayable) -> Unit,
    onCompleteClick: (TaskDisplayable) -> Unit,
    selectedPage: Int,
) {
    var expandedTask by remember { mutableStateOf<TaskDisplayable?>(null) }
    var expandedTaskIndex by remember { mutableStateOf<Int?>(null) }
    //collapse expanded tasks on page changed
    LaunchedEffect(selectedPage) {
        expandedTask = null
        expandedTaskIndex = null
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = date,
            style = LocalTextStyle.current.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(8.dp)
        )
        val listState = rememberLazyListState()
        val localDensity = LocalDensity.current
        //scroll by buttons height so the last task on the list is fully visible when expanded
        LaunchedEffect(expandedTaskIndex) {
            expandedTaskIndex?.let {
                val scrollOffset = with(localDensity) { 56.dp.toPx() }
                if(it > 2) listState.animateScrollBy(scrollOffset)
            }
        }
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(items = tasks){ index, task ->
                TaskCard(
                    task = task,
                    onEditClick = onEditClick,
                    onCompleteClick = {
                        expandedTask = null
                        expandedTaskIndex = null
                        onCompleteClick(it)
                    },
                    expandedTaskId = expandedTask?.id,
                    onTaskClick = {
                        expandedTaskIndex = if(expandedTask == it) null else index
                        expandedTask = if(expandedTask == it) null else it
                    }
                )
            }
        }
    }
}