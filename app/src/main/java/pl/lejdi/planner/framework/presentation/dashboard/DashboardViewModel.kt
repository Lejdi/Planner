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
    override fun setInitialState() : DashboardContract.State{
        return DashboardContract.State(
            isLoading = true,
            errors = errorsQueue,
            daysTasksMap = emptyMap()
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
            is DashboardContract.Event.TaskClicked -> {
                setEffect {
                    DashboardContract.Effect.NavigateToDetails(taskDisplayableMapper.mapToBusinessModel(event.task))
                }
            }
        }
    }
}