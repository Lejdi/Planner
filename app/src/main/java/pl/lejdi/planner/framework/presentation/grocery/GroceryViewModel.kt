package pl.lejdi.planner.framework.presentation.grocery

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.lejdi.planner.business.data.model.GroceryItem
import pl.lejdi.planner.business.usecases.UseCaseResult
import pl.lejdi.planner.business.usecases.grocery.GroceryUseCases
import pl.lejdi.planner.framework.presentation.common.BaseViewModel
import pl.lejdi.planner.framework.presentation.util.ErrorType
import javax.inject.Inject

@HiltViewModel
class GroceryViewModel @Inject constructor(
    private val groceryUseCases: GroceryUseCases
) : BaseViewModel<GroceryContract.Event, GroceryContract.State, GroceryContract.Effect>() {

    init {
        groceryUseCases.getAllGroceries(
            params = Unit,
            scope = viewModelScope,
            onResult = { result ->
                setState {
                    copy(
                        isLoading = false,
                    )
                }
                if(result.isFailure) errorsQueue.addError(ErrorType.Unknown)
                else{
                    viewModelScope.launch {
                        result.getOrNull()?.collect{ useCaseResult ->
                            when(useCaseResult){
                                is UseCaseResult.Error -> errorsQueue.addError(useCaseResult.error)
                                is UseCaseResult.Success -> {
                                    setState {
                                        copy(
                                            groceriesList = useCaseResult.data
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }

    override fun setInitialState(): GroceryContract.State {
        return GroceryContract.State(
            isLoading = false,
            errors = errorsQueue,
            groceriesList = emptyList(),
            newTaskExpanded = false
        )
    }

    override fun sendEvent(event: GroceryContract.Event) {
        when(event){
            GroceryContract.Event.AddButtonClicked -> {
                setState {
                    copy(
                        newTaskExpanded = true
                    )
                }
            }
            is GroceryContract.Event.SaveNewTaskClicked -> {
                setState {
                    copy(
                        isLoading = true
                    )
                }
                groceryUseCases.addGrocery(
                    params = GroceryItem(
                        id = 0,
                        name = event.name,
                        description = event.description
                    ),
                    scope = viewModelScope,
                    onResult = { result ->
                        if (result.isFailure) errorsQueue.addError(ErrorType.Unknown)
                        setState {
                            copy(
                                isLoading = false,
                                newTaskExpanded = false,
                            )
                        }
                    }
                )
            }

            is GroceryContract.Event.GroceryCompleted -> {
                setState {
                    copy(
                        isLoading = true
                    )
                }
                groceryUseCases.deleteGrocery(
                    params = event.item,
                    scope = viewModelScope,
                    onResult = { result ->
                        if (result.isFailure) errorsQueue.addError(ErrorType.Unknown)
                        setState {
                            copy(
                                isLoading = false
                            )
                        }
                    }
                )
            }
            is GroceryContract.Event.GroceryEdited -> {
                setState {
                    copy(
                        isLoading = true
                    )
                }
                groceryUseCases.editGrocery(
                    params = event.item,
                    scope = viewModelScope,
                    onResult = { result ->
                        if (result.isFailure) errorsQueue.addError(ErrorType.Unknown)
                        setState {
                            copy(
                                isLoading = false
                            )
                        }
                    }
                )
            }
        }
    }
}