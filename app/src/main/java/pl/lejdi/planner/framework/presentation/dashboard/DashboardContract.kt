package pl.lejdi.planner.framework.presentation.dashboard

import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.framework.presentation.common.model.task.SingleDayDataDTO
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayable
import pl.lejdi.planner.framework.presentation.util.ErrorsQueue
import pl.lejdi.planner.framework.presentation.util.ViewEffect
import pl.lejdi.planner.framework.presentation.util.ViewEvent
import pl.lejdi.planner.framework.presentation.util.ViewState

class DashboardContract  {

    sealed class Event : ViewEvent {
        data class EditButtonClicked(val task: TaskDisplayable) : Event()
        data class CompleteButtonClicked(val task: TaskDisplayable) : Event()
        data object RefreshTasks : Event()
        data object AddButtonClicked: Event()
        data object DeleteOutdatedTasks: Event()
    }

    data class State(
        var isLoading: Boolean,
        var errors: ErrorsQueue,
        var daysTasksMap: List<SingleDayDataDTO>
    ) : ViewState

    sealed class Effect : ViewEffect {
        data class NavigateToDetails(val task: Task?) : Effect()
    }
}