package pl.lejdi.planner.framework.presentation.grocery

import pl.lejdi.planner.framework.presentation.util.ErrorsQueue
import pl.lejdi.planner.framework.presentation.util.ViewEffect
import pl.lejdi.planner.framework.presentation.util.ViewEvent
import pl.lejdi.planner.framework.presentation.util.ViewState

class GroceryContract  {

    sealed class Event : ViewEvent {
    }

    data class State(
        var isLoading: Boolean,
        var errors: ErrorsQueue,
        var groceriesList: List<String>
    ) : ViewState

    sealed class Effect : ViewEffect {
    }
}