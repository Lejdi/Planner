package pl.lejdi.planner.framework.presentation.taskslist

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.lejdi.planner.business.usecases.UseCaseResult
import pl.lejdi.planner.business.usecases.taskslist.TasksListUseCases
import pl.lejdi.planner.framework.presentation.common.BaseViewModel
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayableMapper
import pl.lejdi.planner.framework.presentation.util.ErrorType
import javax.inject.Inject

@HiltViewModel
class TasksListViewModel @Inject constructor(
    private val tasksListUseCases: TasksListUseCases,
    private val taskDisplayableMapper: TaskDisplayableMapper
) : BaseViewModel<TasksListContract.Event, TasksListContract.State, TasksListContract.Effect>() {
    override fun setInitialState() : TasksListContract.State{
        return TasksListContract.State(
            isLoading = true,
            errors = errorsQueue,
            daysTasksMap = emptyMap()
        )
    }

    override fun sendEvent(event: TasksListContract.Event) {
        when(event){
            is TasksListContract.Event.RefreshTasks -> {
                setState {
                    copy(
                        isLoading = true
                    )
                }
                tasksListUseCases.getTasksForDashboard(
                    params = Unit,
                    scope = viewModelScope,
                    onResult = { result ->
                        if(result.isFailure) errorsQueue.addError(ErrorType.Unknown)
                        else{
                            result.getOrNull()?.let { useCaseResult ->
                                when(useCaseResult){
                                    is UseCaseResult.Error -> errorsQueue.addError(useCaseResult.error)
                                    is UseCaseResult.Success -> {
                                        setState {
                                            copy(
                                                isLoading = false,
                                                daysTasksMap = useCaseResult.data
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                )
            }
            is TasksListContract.Event.TaskClicked -> {
                setEffect {
                    TasksListContract.Effect.NavigateToDetails(taskDisplayableMapper.mapToBusinessModel(event.task))
                }
            }
        }
    }
}