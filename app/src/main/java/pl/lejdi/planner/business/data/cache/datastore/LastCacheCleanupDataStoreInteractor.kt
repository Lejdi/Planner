package pl.lejdi.planner.business.data.cache.datastore

import pl.lejdi.planner.business.utils.date.DateFormatter
import pl.lejdi.planner.framework.datasource.datastore.DataStoreWrapper
import java.util.Date

class LastCacheCleanupDataStoreInteractor(
    preferencesDataStoreWrapper: DataStoreWrapper,
    private val dateFormatter: DateFormatter
) : DataStoreInteractor<Date>(preferencesDataStoreWrapper) {

    override fun getDataStoreKey() = "LAST_CLEANUP_DATE_KEY"

    override suspend fun getData(): Date? {
        val dataStoreResult = getValueFromDataStore()
        return dateFormatter.dateFromCacheFormat(dataStoreResult)
    }

    override suspend fun setData(dataToSet: Date) {
        val date = dateFormatter.formatDateForCache(dataToSet)
        putValueInDataStore(date!!)
    }
}