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
import androidx.compose.ui.platform.testTag
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
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenTestTags.EDIT_TASK_SCREEN
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenTestTags.EDIT_TASK_SCREEN_DELETE_BUTTON
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenTestTags.EDIT_TASK_SCREEN_END_DATE_FIELD
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenTestTags.EDIT_TASK_SCREEN_HOUR_FIELD
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenTestTags.EDIT_TASK_SCREEN_SAVE_BUTTON
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenTestTags.EDIT_TASK_SCREEN_START_DATE_FIELD
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenTestTags.EDIT_TASK_SCREEN_TASK_DESCRIPTION_FIELD
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenTestTags.EDIT_TASK_SCREEN_TASK_NAME_FIELD
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
    navigateBack: () -> Unit,
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
                    navigateBack()
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
        val daysInterval = remember {
            val savedInterval = taskDetails?.daysInterval
            mutableStateOf(if(savedInterval == 0) null else savedInterval)
        }

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
                    .testTag(EDIT_TASK_SCREEN)
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
                        validation = formValidator.addValidation(NotEmptyValidation),
                        modifier = Modifier
                            .testTag(EDIT_TASK_SCREEN_TASK_NAME_FIELD)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    FormTextField(
                        value = taskDescription,
                        onValueChange = {
                            taskDescription = it
                        },
                        label = stringResource(R.string.task_description_label),
                        minLines = 3,
                        modifier = Modifier
                            .testTag(EDIT_TASK_SCREEN_TASK_DESCRIPTION_FIELD)
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
                                modifier = Modifier
                                    .testTag(EDIT_TASK_SCREEN_START_DATE_FIELD)
                            )
                            TimePickerField(
                                initialTime = selectedTime,
                                onTimeSelected = {
                                    selectedTime = it
                                },
                                label = stringResource(R.string.hour_label),
                                modifier = Modifier
                                    .testTag(EDIT_TASK_SCREEN_HOUR_FIELD)
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
                            },
                            modifier = Modifier
                                .testTag(EDIT_TASK_SCREEN_END_DATE_FIELD)
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
                                navigateBack()
                            } else {
                                viewModel.sendEvent(EditTaskContract.Event.DeleteTask(taskDetails))
                            }
                        },
                        modifier = Modifier
                            .testTag(EDIT_TASK_SCREEN_DELETE_BUTTON)
                    ) { Text(stringResource(R.string.delete_button)) }
                    Button(
                        onClick = {
                            if (formValidator.validate()) {
                                val isAsap = selectedRadio == EditTaskRadioButton.ASAP
                                val task = Task(
                                    id = taskDetails?.id ?: 0,
                                    name = taskName,
                                    description = taskDescription.ifEmpty { null },
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
                        },
                        modifier = Modifier
                            .testTag(EDIT_TASK_SCREEN_SAVE_BUTTON)
                    ) { Text(stringResource(R.string.save_button)) }
                }
            }
        }
    }
}

object EditTaskScreenTestTags{
    const val EDIT_TASK_SCREEN = "EditTaskScreen"
    const val EDIT_TASK_SCREEN_SAVE_BUTTON = "EditTaskScreen.Button.Save"
    const val EDIT_TASK_SCREEN_DELETE_BUTTON = "EditTaskScreen.Button.Delete"
    const val EDIT_TASK_SCREEN_TASK_NAME_FIELD = "EditTaskScreen.Field.TaskName"
    const val EDIT_TASK_SCREEN_TASK_DESCRIPTION_FIELD = "EditTaskScreen.Field.TaskDescription"
    const val EDIT_TASK_SCREEN_START_DATE_FIELD = "EditTaskScreen.Field.StartDate"
    const val EDIT_TASK_SCREEN_END_DATE_FIELD = "EditTaskScreen.Field.EndDate"
    const val EDIT_TASK_SCREEN_HOUR_FIELD = "EditTaskScreen.Field.Hour"
}