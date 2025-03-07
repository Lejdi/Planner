package pl.lejdi.planner.framework.presentation.common.ui.components.datepicker

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pl.lejdi.planner.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlannerDatePicker(
    dismiss: () -> Unit,
    applyDate: () -> Unit,
    state: DatePickerState
) {
    DatePickerDialog(
        onDismissRequest = {
            dismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    applyDate()
                    dismiss()
                }
            ) {
                Text(stringResource(R.string.confirm_button))
            }
        },
        content = {
            DatePicker(
                state = state
            )
        }
    )
}