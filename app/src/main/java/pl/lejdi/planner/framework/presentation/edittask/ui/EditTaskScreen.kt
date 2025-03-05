package pl.lejdi.planner.framework.presentation.edittask.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.Serializable
import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.business.data.model.Time
import pl.lejdi.planner.business.utils.date.today
import pl.lejdi.planner.framework.presentation.common.ui.components.PlannerDatePicker
import pl.lejdi.planner.framework.presentation.common.ui.components.PlannerTimePicker
import pl.lejdi.planner.framework.presentation.edittask.EditTaskContract
import pl.lejdi.planner.framework.presentation.edittask.EditTaskViewModel
import pl.lejdi.planner.framework.presentation.edittask.util.EditTaskFormHelper
import pl.lejdi.planner.framework.presentation.edittask.util.EditTaskRadioButton
import java.util.Date

@Serializable
data class EditTaskScreenRoute(
    val taskDetails: Task?
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    taskDetails: Task?,
    navigateBack: () -> Unit,
    viewModel: EditTaskViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel.effect) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is EditTaskContract.Effect.NavigateBack -> {
                    navigateBack()
                }
            }
        }.collect()
    }
    Box {
        val taskName = remember { mutableStateOf(taskDetails?.name ?: "") }
        val taskDescription = remember { mutableStateOf(taskDetails?.description ?: "") }
        val selectedStartDate = remember { mutableStateOf(taskDetails?.startDate ?: Date()) }
        val selectedEndDate = remember { mutableStateOf(taskDetails?.endDate) }
        val selectedTime = remember { mutableStateOf(taskDetails?.hour) }
        val daysInterval = remember { mutableStateOf(taskDetails?.daysInterval) }

        val taskSelectedRadio = EditTaskFormHelper.getSelectedRadioForTask(taskDetails)
        val (selectedRadio, onRadioSelect) = remember { mutableStateOf(taskSelectedRadio) }

        val showStartDatePickerDialog = remember { mutableStateOf(false) }
        val startDatePickerState = rememberDatePickerState().apply {
            val initialTime = taskDetails?.startDate ?: today()
            selectedDateMillis = initialTime.time
        }

        val showEndDatePickerDialog = remember { mutableStateOf(false) }
        val endDatePickerState = rememberDatePickerState(
            selectableDates = object : SelectableDates{
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis > selectedStartDate.value.time
                }
            }
        ).apply {
            val initialTime = taskDetails?.endDate ?: today()
            selectedDateMillis = initialTime.time
        }

        val showTimePickerDialog = remember { mutableStateOf(false) }
        val timePickerState = rememberTimePickerState(
            is24Hour = true
        ).apply {
            taskDetails?.hour?.let {
                hour = it.hour
                minute = it.minute
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = taskName.value,
                onValueChange = {
                    taskName.value = it
                },
                label = { Text("enter task name") },
                maxLines = 1
            )
            OutlinedTextField(
                value = taskDescription.value,
                onValueChange = {
                    taskDescription.value = it
                },
                label = { Text("enter task description") },
                minLines = 3
            )
            RadioButtonsContainer(
                selectedRadio = selectedRadio,
                onRadioSelect = onRadioSelect,
                daysInterval = daysInterval
            )
            if (selectedRadio != EditTaskRadioButton.ASAP) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        enabled = false,
                        modifier = Modifier
                            .width(150.dp)
                            .clickable {
                                showStartDatePickerDialog.value = true
                            },
                        value = EditTaskFormHelper.formatDateForDisplay(
                            LocalContext.current,
                            selectedStartDate.value
                        ),
                        onValueChange = {},
                        label = {
                            val labelText =
                                if (selectedRadio == EditTaskRadioButton.SPECIFIC_DAY) "Date"
                                else "Start Date"
                            Text(labelText)
                        }
                    )

                    OutlinedTextField(
                        enabled = false,
                        modifier = Modifier
                            .width(100.dp)
                            .clickable {
                                showTimePickerDialog.value = true
                            },
                        value = EditTaskFormHelper.formatHoursForDisplay(
                            LocalContext.current,
                            selectedTime.value
                        ),
                        onValueChange = {},
                        label = {
                            Text("Hour")
                        }
                    )
                }
            }

            if(selectedRadio == EditTaskRadioButton.PERIODIC){
                OutlinedTextField(
                    enabled = false,
                    modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            showEndDatePickerDialog.value = true
                        },
                    value = EditTaskFormHelper.formatDateForDisplay(
                        LocalContext.current,
                        selectedEndDate.value
                    ),
                    onValueChange = {},
                    label = {
                        Text("End Date")
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        if(taskDetails == null){
                            navigateBack()
                        }
                        else {
                            viewModel.sendEvent(EditTaskContract.Event.DeleteTask(taskDetails))
                        }
                    }
                ) { Text("Delete") }
                Button(
                    onClick = {
                        val task = Task(
                            id = taskDetails?.id ?: 0,
                            name = taskName.value,
                            description = taskDescription.value,
                            startDate = selectedStartDate.value,
                            endDate = selectedEndDate.value,
                            hour = selectedTime.value,
                            daysInterval = daysInterval.value ?: 0,
                            asap = selectedRadio == EditTaskRadioButton.ASAP
                        )
                        val event = if(taskDetails == null){
                            EditTaskContract.Event.AddTask(task)
                        }
                        else {
                            EditTaskContract.Event.EditTask(task)
                        }
                        viewModel.sendEvent(event)
                    }
                ) { Text("Save") }
            }
        }

        if (showStartDatePickerDialog.value) {
            PlannerDatePicker(
                dismiss = {
                    showStartDatePickerDialog.value = false
                },
                applyDate = {
                    selectedStartDate.value =
                        startDatePickerState.selectedDateMillis?.let { Date(it) } ?: today()
                },
                state = startDatePickerState
            )
        }
        if (showEndDatePickerDialog.value) {
            PlannerDatePicker(
                dismiss = {
                    showEndDatePickerDialog.value = false
                },
                applyDate = {
                    selectedEndDate.value = endDatePickerState.selectedDateMillis?.let { Date(it) }
                },
                state = endDatePickerState
            )
        }
        if (showTimePickerDialog.value) {
            PlannerTimePicker(
                dismiss = {
                    showTimePickerDialog.value = false
                },
                applyTime = {
                    selectedTime.value = Time(timePickerState.hour, timePickerState.minute)
                },
                state = timePickerState
            )
        }
    }
}