package pl.lejdi.planner.business.data.cache.datastore

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.lejdi.planner.framework.datasource.datastore.DataStoreWrapper

abstract class DataStoreInteractor <T>(
    private val preferencesDataStoreWrapper: DataStoreWrapper,
) {
    protected abstract fun getDataStoreKey() : String
    abstract suspend fun getData() : T?
    abstract suspend fun setData(dataToSet: T)

    protected suspend fun putValueInDataStore(value: String) {
        withContext(Dispatchers.IO) {
            try {
                preferencesDataStoreWrapper.updateValue(getDataStoreKey(), value)
            } catch (throwable: Throwable) {
                //do nothing
            }
        }
    }

    protected suspend fun getValueFromDataStore() : String? {
        return withContext(Dispatchers.IO) {
            try {
                preferencesDataStoreWrapper.getValue(getDataStoreKey()).ifEmpty { null }
            } catch (throwable: Throwable) {
                null
            }
        }
    }
}