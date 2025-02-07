package pl.lejdi.planner.framework.presentation.edittask

import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayable
import pl.lejdi.planner.framework.presentation.util.ErrorsQueue
import pl.lejdi.planner.framework.presentation.util.ViewEffect
import pl.lejdi.planner.framework.presentation.util.ViewEvent
import pl.lejdi.planner.framework.presentation.util.ViewState

class EditTaskContract  {

    sealed class Event : ViewEvent {
        data class AddTask(val taskId: String) : Event()
        data object NavigateBack : Event()
    }

    data class State(
        var isLoading: Boolean,
        var errors: ErrorsQueue,
        var taskDetails: Task?
    ) : ViewState

    sealed class Effect : ViewEffect {

    }
}