package pl.lejdi.planner.framework.presentation.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.lejdi.planner.framework.presentation.util.ErrorsQueue
import pl.lejdi.planner.framework.presentation.util.ViewEffect
import pl.lejdi.planner.framework.presentation.util.ViewEvent
import pl.lejdi.planner.framework.presentation.util.ViewState

abstract class BaseViewModel<Event : ViewEvent, UiState : ViewState, Effect : ViewEffect>
    : ViewModel() {

    val errorsQueue = ErrorsQueue()

    private val initialState: UiState by lazy { setInitialState() }
    abstract fun setInitialState(): UiState

    private val _viewState: MutableState<UiState> = mutableStateOf(initialState)
    val viewState: State<UiState> = _viewState

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    abstract fun sendEvent(event: Event)

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }
}
