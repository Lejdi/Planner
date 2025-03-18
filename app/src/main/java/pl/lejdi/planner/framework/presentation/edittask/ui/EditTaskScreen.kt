package pl.lejdi.planner.framework.presentation.edittask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.Serializable
import pl.lejdi.planner.R
import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.business.utils.date.DateUtil
import pl.lejdi.planner.business.utils.date.daysSinceDate
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

@Serializable
data class EditTaskScreenRoute(
    val taskDetails: Task?
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    taskDetails: Task?,
    navigateBack: (Boolean) -> Unit,
    viewModel: EditTaskViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    dateUtil: DateUtil
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
    Box(
        modifier = modifier
    ) {
        val formValidator = remember { FormValidator() }

        var taskName by remember { mutableStateOf(taskDetails?.name ?: "") }
        var taskDescription by remember { mutableStateOf(taskDetails?.description ?: "") }
        var selectedStartDate by remember { mutableStateOf(taskDetails?.startDate ?: dateUtil.getToday()) }
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
                Column(
                    modifier = Modifier
                        .weight(1.0f)
                ) {
                    FormTextField(
                        value = taskName,
                        onValueChange = {
                            taskName = it
                        },
                        label = stringResource(R.string.task_name_label),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        validation = formValidator.addValidation(NotEmptyValidation)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    FormTextField(
                        value = taskDescription,
                        onValueChange = {
                            taskDescription = it
                        },
                        label = stringResource(R.string.task_description_label),
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
                                label = if (selectedRadio == EditTaskRadioButton.SPECIFIC_DAY) stringResource(R.string.date_label) else stringResource(R.string.start_date_label),
                                onDateSelected = {
                                    selectedStartDate = it ?: dateUtil.getToday()
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
                                label = stringResource(R.string.hour_label)
                            )
                        }
                    }
                    if (selectedRadio == EditTaskRadioButton.PERIODIC) {
                        DatePickerField(
                            value = EditTaskFormHelper.formatDateForDisplay(
                                LocalContext.current,
                                selectedEndDate
                            ),
                            label = stringResource(R.string.end_date_label),
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
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
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
                    ) { Text(stringResource(R.string.delete_button)) }
                    Button(
                        onClick = {
                            if (formValidator.validate()) {
                                val isAsap = selectedRadio == EditTaskRadioButton.ASAP
                                val task = Task(
                                    id = taskDetails?.id ?: 0,
                                    name = taskName,
                                    description = taskDescription,
                                    startDate = if(isAsap) dateUtil.getToday() else selectedStartDate,
                                    endDate = selectedEndDate,
                                    hour = selectedTime,
                                    daysInterval = if(isAsap) 0 else daysInterval.value ?: 0,
                                    asap = isAsap
                                )
                                val event = if (taskDetails == null) {
                                    EditTaskContract.Event.AddTask(task)
                                } else {
                                    EditTaskContract.Event.EditTask(task)
                                }
                                viewModel.sendEvent(event)
                            }
                        }
                    ) { Text(stringResource(R.string.save_button)) }
                }
            }
        }
    }
}