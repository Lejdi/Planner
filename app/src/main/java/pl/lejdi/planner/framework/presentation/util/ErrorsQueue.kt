package pl.lejdi.planner.framework.presentation.util

import androidx.compose.runtime.mutableStateOf

class ErrorsQueue {
    private val errors = mutableStateOf(listOf<ErrorType>())

    fun getError(): ErrorType?{
        return errors.value.getOrNull(0)
    }

    fun removeError() {
        val temp = mutableListOf<ErrorType>()
        temp.addAll(errors.value)
        if(temp.isNotEmpty()) temp.removeAt(0)
        errors.value = temp
    }

    fun addError(error: ErrorType){
        val newErrorsList = errors.value.toMutableList()
        newErrorsList.add(error)
        errors.value = newErrorsList
    }
}