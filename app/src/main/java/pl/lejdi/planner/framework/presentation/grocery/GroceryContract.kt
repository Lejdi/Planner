package pl.lejdi.planner.framework.presentation.grocery

import pl.lejdi.planner.framework.presentation.common.model.grocery.GroceryDisplayable
import pl.lejdi.planner.framework.presentation.util.ErrorsQueue
import pl.lejdi.planner.framework.presentation.util.ViewEffect
import pl.lejdi.planner.framework.presentation.util.ViewEvent
import pl.lejdi.planner.framework.presentation.util.ViewState

class GroceryContract  {

    sealed class Event : ViewEvent {
        data class GroceryEdited(val item: GroceryDisplayable) : Event()
        data class GroceryCompleted(val item: GroceryDisplayable) : Event()
        data object AddButtonClicked: Event()
        data class SaveNewTaskClicked(val name: String, val description: String) : Event()
    }

    data class State(
        var isLoading: Boolean,
        var errors: ErrorsQueue,
        var groceriesList: List<GroceryDisplayable>,
        var newTaskExpanded: Boolean
    ) : ViewState

    sealed class Effect : ViewEffect
}