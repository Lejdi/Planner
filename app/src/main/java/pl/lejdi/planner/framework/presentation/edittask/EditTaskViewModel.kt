package pl.lejdi.planner.framework.presentation.edittask

import dagger.hilt.android.lifecycle.HiltViewModel
import pl.lejdi.planner.business.usecases.edittask.EditTaskUseCases
import pl.lejdi.planner.framework.presentation.common.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    val editTaskUseCases: EditTaskUseCases
)  : BaseViewModel<EditTaskContract.Event, EditTaskContract.State, EditTaskContract.Effect>() {
    override fun setInitialState() = EditTaskContract.State(
        isLoading = true,
        errors = errorsQueue,
        taskDetails = null
    )

    override fun sendEvent(event: EditTaskContract.Event) {
        //todo
    }
}