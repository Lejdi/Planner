package pl.lejdi.planner.framework.presentation.dashboard.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayable
import pl.lejdi.planner.framework.presentation.common.ui.utils.clickableWithoutRipple

@Composable
fun TaskCard(
    task: TaskDisplayable,
    expandedTaskId: Int?,
    onEditClick: (TaskDisplayable) -> Unit,
    onCompleteClick: (TaskDisplayable) -> Unit,
    onTaskClick: (TaskDisplayable) -> Unit,
) {
    val shouldBeExpanded = task.id == expandedTaskId
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = Modifier
            .animateContentSize()
            .clickableWithoutRipple {
                onTaskClick(task)
            },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(if (shouldBeExpanded) 1.0f else 0.9f)
                .padding(12.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = task.name,
                    style = LocalTextStyle.current.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .weight(1.0f)
                )
                task.hour?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            task.description?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            if (shouldBeExpanded) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            onEditClick(task)
                        }
                    ) {
                        Text("Edit")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Outlined.Edit, null)
                    }
                    Button(
                        onClick = {
                            onCompleteClick(task)
                        }
                    ) {
                        Text("Complete")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Outlined.CheckCircle, null)
                    }
                }
            }
        }
    }
}