package pl.lejdi.planner.framework.presentation.common.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pl.lejdi.planner.business.utils.date.today
import pl.lejdi.planner.framework.presentation.common.ui.utils.clickableWithoutRipple
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    value: String,
    label: String,
    onDateSelected: (Date?) -> Unit,
    initialDate: Date? = null,
    selectableDates: SelectableDates = DatePickerDefaults.AllDates
) {
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        selectableDates = selectableDates
    ).apply {
        val initialTime = initialDate ?: today()
        selectedDateMillis = initialTime.time + 12*60*60*1000 //set time to mid of day - midnight is displayed wrong
    }

    Box{
        OutlinedTextField(
            enabled = false,
            modifier = Modifier
                .width(150.dp)
                .clickableWithoutRipple {
                    showDatePickerDialog = true
                },
            value = value,
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
        if (showDatePickerDialog) {
            PlannerDatePicker(
                dismiss = {
                    showDatePickerDialog = false
                },
                applyDate = {
                    onDateSelected(datePickerState.selectedDateMillis?.let { Date(it) })
                },
                state = datePickerState
            )
        }
    }

}