package pl.lejdi.planner.framework.presentation.common.ui.components.timepicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import pl.lejdi.planner.business.data.model.Time
import pl.lejdi.planner.framework.presentation.common.ui.utils.clickableWithoutRipple
import pl.lejdi.planner.framework.presentation.edittask.util.EditTaskFormHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerField(
    initialTime: Time? = null,
    onTimeSelected: (Time) -> Unit,
    label: String,
) {
    var showTimePickerDialog by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(
        is24Hour = true
    ).apply {
        initialTime?.let {
            hour = it.hour
            minute = it.minute
        }
    }
    Box{
        OutlinedTextField(
            enabled = false,
            modifier = Modifier
                .width(100.dp)
                .clickableWithoutRipple {
                    showTimePickerDialog = true
                },
            value = EditTaskFormHelper.formatHoursForDisplay(
                LocalContext.current,
                initialTime
            ),
            onValueChange = {},
            label = {
                Text(label)
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = Color.Transparent,
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        )
    }
    if (showTimePickerDialog) {
        PlannerTimePicker(
            dismiss = {
                showTimePickerDialog = false
            },
            applyTime = {
                onTimeSelected(Time(timePickerState.hour, timePickerState.minute))
            },
            state = timePickerState
        )
    }
}