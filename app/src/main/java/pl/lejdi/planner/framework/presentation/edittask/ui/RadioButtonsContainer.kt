package pl.lejdi.planner.framework.presentation.edittask.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import pl.lejdi.planner.framework.presentation.edittask.util.EditTaskRadioButton

@Composable
fun RadioButtonsContainer(
    selectedRadio: EditTaskRadioButton,
    onRadioSelect: (EditTaskRadioButton) -> Unit,
    daysInterval: MutableState<Int?>
) {
    Column {
        EditTaskRadioButton.entries.forEach { radioButtonOption ->
            val radioSelected = (radioButtonOption == selectedRadio)
            Column {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = radioSelected,
                            onClick = {
                                onRadioSelect(radioButtonOption)
                            }
                        )
                        .padding(horizontal = 16.dp)
                ) {
                    RadioButton(
                        selected = radioSelected,
                        onClick = { onRadioSelect(radioButtonOption) }
                    )
                    Text(
                        text = radioButtonOption.description,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                if(radioSelected){
                    AdditionalRadioComponents(
                        radioButton = radioButtonOption,
                        daysInterval = daysInterval
                    )
                }
            }
        }
    }
}

@Composable
fun AdditionalRadioComponents(
    radioButton: EditTaskRadioButton,
    daysInterval: MutableState<Int?>
) {
    when(radioButton){
        EditTaskRadioButton.PERIODIC -> {
            var textFieldState by remember {
                val initialState = daysInterval.value?.toString().orEmpty()
                mutableStateOf(initialState)
            }
            Row{
                Text("Repeat every")
                OutlinedTextField(
                    value = textFieldState,
                    onValueChange = {
                        textFieldState = it
                        daysInterval.value = it.toIntOrNull() ?: 0
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.width(64.dp)
                )
                Text("days")
            }

        }
        else -> {}
    }
}