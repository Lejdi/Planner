package pl.lejdi.planner.framework.presentation.taskslist

import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayable
import pl.lejdi.planner.framework.presentation.util.ErrorsQueue
import pl.lejdi.planner.framework.presentation.util.ViewEffect
import pl.lejdi.planner.framework.presentation.util.ViewEvent
import pl.lejdi.planner.framework.presentation.util.ViewState

class TasksListContract  {

    sealed class Event : ViewEvent {
        data class TaskClicked(val taskId: String) : Event()
        data object RefreshTasks : Event()
    }

    data class State(
        var isLoading: Boolean,
        var errors: ErrorsQueue,
        var tasksList: List<TaskDisplayable>
    ) : ViewState

    sealed class Effect : ViewEffect {
        data class NavigateToDetails(val taskId: String) : Effect()
    }
}