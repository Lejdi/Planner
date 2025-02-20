package pl.lejdi.planner.business.data.cache.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

private const val CACHE_TIMEOUT = 2000L

suspend fun <T> safeCacheCall(
    dispatcher: CoroutineDispatcher,
    cacheCall: suspend () -> T
): CacheResult<T> {
    return withContext(dispatcher) {
        try {
            withTimeout(CACHE_TIMEOUT){
                CacheResult.Success(cacheCall.invoke())
            }
        } catch (throwable: Throwable) {
            CacheResult.Error()
        }
    }
}
