package pl.lejdi.planner.framework.presentation.dashboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayable

@Composable
fun SingleDayView(
    date: String,
    tasks: List<TaskDisplayable>,
    onEditClick: (TaskDisplayable) -> Unit,
    onCompleteClick: (TaskDisplayable) -> Unit
) {
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
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = tasks){ task ->
                TaskCard(
                    task = task,
                    onEditClick = onEditClick,
                    onCompleteClick = onCompleteClick
                )
            }
        }
    }
}