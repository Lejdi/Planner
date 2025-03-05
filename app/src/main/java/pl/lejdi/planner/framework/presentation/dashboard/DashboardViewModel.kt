package pl.lejdi.planner.framework.presentation.dashboard

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.lejdi.planner.business.usecases.UseCaseResult
import pl.lejdi.planner.business.usecases.dashboard.DashboardUseCases
import pl.lejdi.planner.framework.presentation.common.BaseViewModel
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayableMapper
import pl.lejdi.planner.framework.presentation.util.ErrorType
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardUseCases: DashboardUseCases,
    private val taskDisplayableMapper: TaskDisplayableMapper
) : BaseViewModel<DashboardContract.Event, DashboardContract.State, DashboardContract.Effect>() {

    init {
        sendEvent(DashboardContract.Event.DeleteOutdatedTasks)
    }

    override fun setInitialState() : DashboardContract.State{
        return DashboardContract.State(
            isLoading = true,
            errors = errorsQueue,
            daysTasksMap = emptyList()
        )
    }

    override fun sendEvent(event: DashboardContract.Event) {
        when(event){
            is DashboardContract.Event.RefreshTasks -> {
                setState {
                    copy(
                        isLoading = true
                    )
                }
                dashboardUseCases.getTasksForDashboard(
                    params = Unit,
                    scope = viewModelScope,
                    onResult = { result ->
                        setState {
                            copy(
                                isLoading = false,
                            )
                        }
                        if(result.isFailure) errorsQueue.addError(ErrorType.Unknown)
                        else{
                            result.getOrNull()?.let { useCaseResult ->
                                when(useCaseResult){
                                    is UseCaseResult.Error -> errorsQueue.addError(useCaseResult.error)
                                    is UseCaseResult.Success -> {
                                        setState {
                                            copy(
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
            is DashboardContract.Event.EditButtonClicked -> {
                setEffect {
                    DashboardContract.Effect.NavigateToDetails(taskDisplayableMapper.mapToBusinessModel(event.task))
                }
            }
            is DashboardContract.Event.AddButtonClicked -> {
                setEffect {
                    DashboardContract.Effect.NavigateToDetails(null)
                }
            }
            is DashboardContract.Event.CompleteButtonClicked -> {
                setState {
                    copy(
                        isLoading = true
                    )
                }
                dashboardUseCases.markTaskComplete(
                    params = event.task,
                    scope = viewModelScope,
                    onResult = { result ->
                        setState {
                            copy(
                                isLoading = false,
                            )
                        }
                        if(result.isFailure) errorsQueue.addError(ErrorType.Unknown)
                        else{
                            result.getOrNull()?.let { useCaseResult ->
                                when(useCaseResult){
                                    is UseCaseResult.Error -> errorsQueue.addError(useCaseResult.error)
                                    is UseCaseResult.Success -> {
                                        sendEvent(DashboardContract.Event.RefreshTasks)
                                    }
                                }
                            }
                        }
                    }
                )
            }

            DashboardContract.Event.DeleteOutdatedTasks -> {
                setState {
                    copy(
                        isLoading = true
                    )
                }
                dashboardUseCases.deleteOutdatedTasks(
                    params = Unit,
                    scope = viewModelScope,
                    onResult = { _ ->
                        setState {
                            copy(
                                isLoading = false,
                            )
                        }
                        sendEvent(DashboardContract.Event.RefreshTasks)
                    }
                )
            }
        }
    }
}