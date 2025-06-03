package pl.lejdi.planner.framework.presentation.grocery

import pl.lejdi.planner.framework.presentation.common.BaseViewModel
import javax.inject.Inject

class GroceryViewModel @Inject constructor(

) : BaseViewModel<GroceryContract.Event, GroceryContract.State, GroceryContract.Effect>() {
    override fun setInitialState(): GroceryContract.State {
        return GroceryContract.State(
            isLoading = false,
            errors = errorsQueue,
            groceriesList = emptyList()
        )
    }

    override fun sendEvent(event: GroceryContract.Event) {

    }
}