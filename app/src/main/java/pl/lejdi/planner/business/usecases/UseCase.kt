package pl.lejdi.planner.business.usecases

import kotlinx.coroutines.*

abstract class UseCase<out Type, in Params> {
    abstract suspend fun execute(params: Params) : Type

    operator fun invoke(
        params: Params,
        scope: CoroutineScope,
        executionDispatcher: CoroutineDispatcher = Dispatchers.IO,
        onResult: (Result<Type>) -> Unit = {}
    ){
        scope.launch {
            val result = withContext(executionDispatcher){
                runCatching { execute(params) }
            }
            onResult(result)
        }
    }
}