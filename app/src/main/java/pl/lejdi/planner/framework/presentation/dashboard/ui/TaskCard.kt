package pl.lejdi.planner.framework.presentation.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayable

@Composable
fun TaskCard(
    task: TaskDisplayable,
    onEditClick: (TaskDisplayable) -> Unit,
    onCompleteClick: (TaskDisplayable) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize(0.9f)
            .background(color = Color.Green)
            .clickable { expanded.value = true },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(task.name)
        task.description?.let { Text(it) }
        if(expanded.value){
            Row {
                Button(
                    onClick = {
                        onEditClick(task)
                    },
                    content = {
                        Text("EDIT")
                    }
                )
                Button(
                    onClick = {
                        onCompleteClick(task)
                    },
                    content = {
                        Text("FINISH")
                    }
                )
            }
        }
    }
}