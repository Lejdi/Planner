package pl.lejdi.planner.business.usecases

import pl.lejdi.planner.framework.presentation.util.ErrorType

sealed class UseCaseResult<DataType>{
    data class Success<DataType>(val data: DataType) : UseCaseResult<DataType>()
    data class Error<DataType>(val error: ErrorType): UseCaseResult<DataType>()
}
