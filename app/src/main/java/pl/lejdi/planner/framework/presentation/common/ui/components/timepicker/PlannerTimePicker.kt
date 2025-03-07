package pl.lejdi.planner.framework.presentation.common.ui.components.timepicker

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TimePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pl.lejdi.planner.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlannerTimePicker(
    dismiss: () -> Unit,
    applyTime: () -> Unit,
    state: TimePickerState
) {
    AlertDialog(
        onDismissRequest = dismiss,
        dismissButton = {
            TextButton(onClick = { dismiss() }) {
                Text(stringResource(R.string.cancel_button))
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    applyTime()
                    dismiss()
                }
            ) {
                Text(stringResource(R.string.confirm_button))
            }
        },
        text = {
            TimePicker(
                state = state,
            )
        }
    )
}