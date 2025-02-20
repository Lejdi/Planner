package pl.lejdi.planner.framework.datasource.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


val Context.plannerDatastore: DataStore<Preferences> by preferencesDataStore(
    name = PreferencesDataStoreWrapper.DATASTORE_NAME
)

class PreferencesDataStoreWrapper(
    private val dataStore: DataStore<Preferences>
) : DataStoreWrapper {

    companion object {
        const val DATASTORE_NAME = "planner_datastore"
    }

    override suspend fun updateValue(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit { mutablePreferences ->
            mutablePreferences[dataStoreKey] = value
        }
    }

    override suspend fun getValue(key: String): String {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey] ?: ""
    }
}