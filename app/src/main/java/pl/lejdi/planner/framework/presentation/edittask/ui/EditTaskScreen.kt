package pl.lejdi.planner.framework.presentation.edittask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.Serializable
import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.business.utils.date.daysSinceDate
import pl.lejdi.planner.business.utils.date.today
import pl.lejdi.planner.framework.presentation.common.ui.BaseScreen
import pl.lejdi.planner.framework.presentation.common.ui.components.datepicker.DatePickerField
import pl.lejdi.planner.framework.presentation.common.ui.components.FormTextField
import pl.lejdi.planner.framework.presentation.common.ui.components.timepicker.TimePickerField
import pl.lejdi.planner.framework.presentation.common.ui.utils.validation.FormValidator
import pl.lejdi.planner.framework.presentation.common.ui.utils.validation.NotEmptyValidation
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
    navigateBack: (Boolean) -> Unit,
    viewModel: EditTaskViewModel = hiltViewModel()
) = BaseScreen(
    displayProgressBar = viewModel.viewState.value.isLoading,
    errorsQueue = viewModel.errorsQueue
) {
    LaunchedEffect(viewModel.effect) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is EditTaskContract.Effect.NavigateBack -> {
                    navigateBack(true)
                }
            }
        }.collect()
    }
    Box {
        val formValidator = remember { FormValidator() }

        var taskName by remember { mutableStateOf(taskDetails?.name ?: "") }
        var taskDescription by remember { mutableStateOf(taskDetails?.description ?: "") }
        var selectedStartDate by remember { mutableStateOf(taskDetails?.startDate ?: Date()) }
        var selectedEndDate by remember { mutableStateOf(taskDetails?.endDate) }
        var selectedTime by remember { mutableStateOf(taskDetails?.hour) }
        val daysInterval = remember { mutableStateOf(taskDetails?.daysInterval) }

        val initialSelectedRadio = EditTaskFormHelper.getSelectedRadioForTask(taskDetails)
        val (selectedRadio, onRadioSelect) = remember { mutableStateOf(initialSelectedRadio) }

        Scaffold { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                    .padding(contentPadding)
                    .padding(16.dp)
            ) {
                FormTextField(
                    value = taskName,
                    onValueChange = {
                        taskName = it
                    },
                    label = "enter task name",
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    validation = formValidator.addValidation(NotEmptyValidation)
                )
                FormTextField(
                    value = taskDescription,
                    onValueChange = {
                        taskDescription = it
                    },
                    label = "enter task description",
                    minLines = 3,
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
                        DatePickerField(
                            value = EditTaskFormHelper.formatDateForDisplay(
                                LocalContext.current,
                                selectedStartDate
                            ),
                            label = if (selectedRadio == EditTaskRadioButton.SPECIFIC_DAY) "Date" else "Start Date",
                            onDateSelected = {
                                selectedStartDate = it ?: today()
                                selectedEndDate?.let { endDate ->
                                    if(selectedStartDate.daysSinceDate(endDate) < 0){
                                        selectedEndDate = selectedStartDate
                                    }
                                }
                            },
                            initialDate = selectedStartDate,
                        )
                        TimePickerField(
                            initialTime = selectedTime,
                            onTimeSelected = {
                                selectedTime = it
                            },
                            label = "Hour"
                        )
                    }
                }
                if (selectedRadio == EditTaskRadioButton.PERIODIC) {
                    DatePickerField(
                        value = EditTaskFormHelper.formatDateForDisplay(
                            LocalContext.current,
                            selectedEndDate
                        ),
                        label = "End date",
                        onDateSelected = {
                            selectedEndDate = it
                        },
                        initialDate = selectedEndDate,
                        selectableDates = object : SelectableDates {
                            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                                return utcTimeMillis > selectedStartDate.time
                            }
                        }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            if (taskDetails == null) {
                                navigateBack(false)
                            } else {
                                viewModel.sendEvent(EditTaskContract.Event.DeleteTask(taskDetails))
                            }
                        }
                    ) { Text("Delete") }
                    Button(
                        onClick = {
                            if (formValidator.validate()) {
                                val task = Task(
                                    id = taskDetails?.id ?: 0,
                                    name = taskName,
                                    description = taskDescription,
                                    startDate = selectedStartDate,
                                    endDate = selectedEndDate,
                                    hour = selectedTime,
                                    daysInterval = daysInterval.value ?: 0,
                                    asap = selectedRadio == EditTaskRadioButton.ASAP
                                )
                                val event = if (taskDetails == null) {
                                    EditTaskContract.Event.AddTask(task)
                                } else {
                                    EditTaskContract.Event.EditTask(task)
                                }
                                viewModel.sendEvent(event)
                            }
                        }
                    ) { Text("Save") }
                }
            }
        }
    }
}