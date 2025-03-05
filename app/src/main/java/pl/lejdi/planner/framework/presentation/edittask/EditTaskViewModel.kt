package pl.lejdi.planner.framework.presentation.edittask

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.lejdi.planner.business.usecases.UseCaseResult
import pl.lejdi.planner.business.usecases.edittask.EditTaskUseCases
import pl.lejdi.planner.framework.presentation.common.BaseViewModel
import pl.lejdi.planner.framework.presentation.util.ErrorType
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    val editTaskUseCases: EditTaskUseCases
) : BaseViewModel<EditTaskContract.Event, EditTaskContract.State, EditTaskContract.Effect>() {
    override fun setInitialState() = EditTaskContract.State(
        isLoading = true,
        errors = errorsQueue,
        taskDetails = null
    )

    override fun sendEvent(event: EditTaskContract.Event) {
        setState {
            copy(
                isLoading = true
            )
        }
        when (event) {
            is EditTaskContract.Event.AddTask -> {
                editTaskUseCases.addTask(
                    params = event.task,
                    scope = viewModelScope,
                    onResult = { result ->
                        handleEditTaskResult(result)
                    }
                )
            }

            is EditTaskContract.Event.DeleteTask -> {
                editTaskUseCases.deleteTask(
                    params = event.task,
                    scope = viewModelScope,
                    onResult = { result ->
                        handleEditTaskResult(result)
                    }
                )
            }

            is EditTaskContract.Event.EditTask -> {
                editTaskUseCases.editTask(
                    params = event.task,
                    scope = viewModelScope,
                    onResult = { result ->
                        handleEditTaskResult(result)
                    }
                )
            }
        }
    }

    private fun handleEditTaskResult(result: Result<UseCaseResult<Unit>>) {
        if (result.isFailure) errorsQueue.addError(ErrorType.Unknown)
        else {
            result.getOrNull()?.let { useCaseResult ->
                when (useCaseResult) {
                    is UseCaseResult.Error -> errorsQueue.addError(useCaseResult.error)
                    is UseCaseResult.Success -> {
                        setState {
                            copy(
                                isLoading = false
                            )
                        }
                        setEffect {
                            EditTaskContract.Effect.NavigateBack
                        }
                    }
                }
            }
        }
    }
}