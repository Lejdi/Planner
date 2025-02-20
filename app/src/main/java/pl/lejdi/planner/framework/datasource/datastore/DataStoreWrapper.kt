package pl.lejdi.planner.framework.datasource.datastore

interface DataStoreWrapper {
    suspend fun updateValue(key: String, value: String)
    suspend fun getValue(key: String): String
}