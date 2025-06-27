package pl.lejdi.planner.framework.presentation.grocery.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AddGroceryView(
    expanded: Boolean,
    onFabClicked: () -> Unit,
    onSaveClicked: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = 16.dp)
    ) {
        if (expanded) {
            Card(
                colors = CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                modifier = Modifier
                    .animateContentSize()
            ) {
                var newTaskName by remember { mutableStateOf("") }
                var newTaskDescription by remember { mutableStateOf("") }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                                .weight(1.0f),
                            textStyle = LocalTextStyle.current.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            placeholder = {
                                Text(
                                    text = "Product name",
                                    style = LocalTextStyle.current.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                )
                            }
                        )
                        OutlinedTextField(
                            value = newTaskDescription,
                            onValueChange = { newValue ->
                                newTaskDescription = newValue
                            },
                            modifier = Modifier
                                .weight(1.0f),
                            placeholder = {
                                Text(
                                    text = "Additional information",
                                    style = LocalTextStyle.current.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                )
                            }
                        )
                    }
                    Button(
                        onClick = {
                            onSaveClicked(newTaskName, newTaskDescription)
                        },
                    ) {
                        Icon(Icons.Outlined.CheckCircle, null)
                    }
                }

            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                FloatingActionButton(
                    onClick = onFabClicked,
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Icon(Icons.Filled.Add, null)
                }
            }
        }
    }
}