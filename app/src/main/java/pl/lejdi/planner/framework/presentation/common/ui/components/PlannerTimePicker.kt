package pl.lejdi.planner.framework.presentation.common.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TimePicker
import androidx.compose.runtime.Composable

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
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    applyTime()
                    dismiss()
                }
            ) {
                Text("OK")
            }
        },
        text = {
            TimePicker(
                state = state,
            )
        }
    )
}