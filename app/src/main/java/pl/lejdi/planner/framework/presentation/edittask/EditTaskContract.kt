package pl.lejdi.planner.framework.presentation.edittask

import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.framework.presentation.util.ErrorsQueue
import pl.lejdi.planner.framework.presentation.util.ViewEffect
import pl.lejdi.planner.framework.presentation.util.ViewEvent
import pl.lejdi.planner.framework.presentation.util.ViewState

class EditTaskContract  {

    sealed class Event : ViewEvent {
        data class AddTask(val task: Task) : Event()
        data class DeleteTask(val task: Task) : Event()
        data class EditTask(val task: Task) : Event()
    }

    data class State(
        var isLoading: Boolean,
        var errors: ErrorsQueue,
    ) : ViewState

    sealed class Effect : ViewEffect {
        data object NavigateBack : Effect()
    }
}