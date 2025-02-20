package pl.lejdi.planner.business.data.cache.util

sealed class CacheResult<DataType>{
    data class Success<DataType>(val data: DataType) : CacheResult<DataType>()
    class Error<DataType>: CacheResult<DataType>()
}