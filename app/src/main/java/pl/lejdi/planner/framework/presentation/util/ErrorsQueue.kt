package pl.lejdi.planner.framework.presentation.util

class ErrorsQueue {
    val errors = mutableListOf<ErrorType>()

    fun getError(): ErrorType{
        return errors.removeAt(0)
    }

    fun addError(error: ErrorType){
        errors.add(error)
    }
}