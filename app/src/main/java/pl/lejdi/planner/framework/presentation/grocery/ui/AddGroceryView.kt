package pl.lejdi.planner.framework.presentation.grocery.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddGroceryView(
    expanded: Boolean,
    onFabClicked: () -> Unit,
    onSaveClicked: (String, String) -> Unit
){
    Column(
        modifier = Modifier
            .height(200.dp)
            .animateContentSize()
    ) {
        if(expanded){
            Card(
                colors = CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
            ) {
                var newTaskName by remember { mutableStateOf("") }
                var newTaskDescription by remember { mutableStateOf("") }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(12.dp),
                ) {
                    OutlinedTextField(
                        value = newTaskName,
                        onValueChange = { newValue ->
                            newTaskName = newValue
                        },
                        modifier = Modifier
                            .weight(1.0f)
                    )
                    OutlinedTextField(
                        value = newTaskDescription,
                        onValueChange = { newValue ->
                            newTaskDescription = newValue
                        },
                        modifier = Modifier
                            .weight(1.0f)
                    )
                    Button(
                        onClick = {
                            onSaveClicked(newTaskName, newTaskDescription)
                        },
                    ) {
                        Icon(Icons.Outlined.CheckCircle, null)
                    }
                }
            }
        }
        else{
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                FloatingActionButton(
                    onClick = onFabClicked,
                ) {
                    Icon(Icons.Filled.Add, null)
                }
            }
        }
    }
}