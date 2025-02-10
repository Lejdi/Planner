package pl.lejdi.planner.framework.presentation.taskslist

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.lejdi.planner.business.usecases.UseCaseResult
import pl.lejdi.planner.business.usecases.taskslist.TasksListUseCases
import pl.lejdi.planner.framework.presentation.common.BaseViewModel
import pl.lejdi.planner.framework.presentation.util.ErrorType
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TasksListViewModel @Inject constructor(
    val tasksListUseCases: TasksListUseCases
) : BaseViewModel<TasksListContract.Event, TasksListContract.State, TasksListContract.Effect>() {
    override fun setInitialState() = TasksListContract.State(
        isLoading = true,
        errors = errorsQueue,
        tasksList = emptyList()
    )

    override fun sendEvent(event: TasksListContract.Event) {
        when(event){
            is TasksListContract.Event.RefreshTasks -> {
                setState {
                    copy(
                        isLoading = true
                    )
                }
                tasksListUseCases.getTasksForDate(
                    params = Date(),
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
                                                tasksList = useCaseResult.data
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
                    //todo temp solution mapper not from usecase
                    TasksListContract.Effect.NavigateToDetails(tasksListUseCases.markTaskComplete.taskDisplayableMapper.mapToBusinessModel(event.task))
                }
            }
        }
    }
}