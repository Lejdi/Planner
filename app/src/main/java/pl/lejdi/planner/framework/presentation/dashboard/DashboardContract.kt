package pl.lejdi.planner.framework.presentation.dashboard

import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayable
import pl.lejdi.planner.framework.presentation.util.ErrorsQueue
import pl.lejdi.planner.framework.presentation.util.ViewEffect
import pl.lejdi.planner.framework.presentation.util.ViewEvent
import pl.lejdi.planner.framework.presentation.util.ViewState
import java.util.Date

class DashboardContract  {

    sealed class Event : ViewEvent {
        data class TaskClicked(val task: TaskDisplayable) : Event()
        data object RefreshTasks : Event()
    }

    data class State(
        var isLoading: Boolean,
        var errors: ErrorsQueue,
        var daysTasksMap: Map<Date, List<TaskDisplayable>>
    ) : ViewState

    sealed class Effect : ViewEffect {
        data class NavigateToDetails(val task: Task) : Effect()
    }
}