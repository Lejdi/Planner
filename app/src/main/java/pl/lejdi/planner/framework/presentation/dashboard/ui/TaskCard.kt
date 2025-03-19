package pl.lejdi.planner.framework.presentation.dashboard.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pl.lejdi.planner.R
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayable
import pl.lejdi.planner.framework.presentation.common.ui.utils.clickableWithoutRipple
import pl.lejdi.planner.framework.presentation.dashboard.ui.TaskCardTestTags.TASK_CARD_COMPLETE_BUTTON
import pl.lejdi.planner.framework.presentation.dashboard.ui.TaskCardTestTags.TASK_CARD_DESCRIPTION
import pl.lejdi.planner.framework.presentation.dashboard.ui.TaskCardTestTags.TASK_CARD_EDIT_BUTTON
import pl.lejdi.planner.framework.presentation.dashboard.ui.TaskCardTestTags.TASK_CARD_HOUR
import pl.lejdi.planner.framework.presentation.dashboard.ui.TaskCardTestTags.TASK_CARD_NAME

@Composable
fun TaskCard(
    task: TaskDisplayable,
    expandedTaskId: Int?,
    onEditClick: (TaskDisplayable) -> Unit,
    onCompleteClick: (TaskDisplayable) -> Unit,
    onTaskClick: (TaskDisplayable) -> Unit,
    modifier: Modifier = Modifier,
) {
    val shouldBeExpanded = task.id == expandedTaskId
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = modifier
            .animateContentSize()
            .clickableWithoutRipple {
                onTaskClick(task)
            },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(if (shouldBeExpanded) 1.0f else 0.9f)
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
                        .testTag(TASK_CARD_NAME)
                )
                task.hour?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .testTag(TASK_CARD_HOUR)
                    )
                }
            }
            Text(
                text = task.description ?: "",
                modifier = Modifier
                    .padding(top = 4.dp)
                    .testTag(TASK_CARD_DESCRIPTION)
            )
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
                        },
                        modifier = Modifier
                            .testTag(TASK_CARD_EDIT_BUTTON)
                    ) {
                        Text(stringResource(R.string.edit_button))
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Outlined.Edit, null)
                    }
                    Button(
                        onClick = {
                            onCompleteClick(task)
                        },
                        modifier = Modifier
                            .testTag(TASK_CARD_COMPLETE_BUTTON)
                    ) {
                        Text(stringResource(R.string.complete_button))
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Outlined.CheckCircle, null)
                    }
                }
            }
        }
    }
}

object TaskCardTestTags {
    const val TASK_CARD_NAME = "TaskCard.Name"
    const val TASK_CARD_DESCRIPTION = "TaskCard.Description"
    const val TASK_CARD_HOUR = "TaskCard.Hour"
    const val TASK_CARD_COMPLETE_BUTTON = "TaskCard.Button.Complete"
    const val TASK_CARD_EDIT_BUTTON = "TaskCard.Button.Edit"
}