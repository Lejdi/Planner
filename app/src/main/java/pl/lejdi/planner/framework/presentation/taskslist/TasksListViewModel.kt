package pl.lejdi.planner.framework.presentation.taskslist

import androidx.lifecycle.viewModelScope
import pl.lejdi.planner.business.usecases.UseCaseResult
import pl.lejdi.planner.business.usecases.common.DeleteTask
import pl.lejdi.planner.business.usecases.taskslist.DeleteOutdatedTasks
import pl.lejdi.planner.business.usecases.taskslist.GetTasksForDate
import pl.lejdi.planner.framework.presentation.common.BaseViewModel
import pl.lejdi.planner.framework.presentation.util.ErrorType
import java.util.Date

class TasksListViewModel(
    val deleteTaskUseCase: DeleteTask,
    val deleteOutdatedTasksUseCase: DeleteOutdatedTasks,
    val getTasksForDateUseCase: GetTasksForDate
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
                getTasksForDateUseCase(
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
                    TasksListContract.Effect.NavigateToDetails(event.taskId)
                }
            }
        }
    }
}