package pl.lejdi.planner.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.lejdi.planner.business.data.cache.datastore.LastCacheCleanupDataStoreInteractor
import pl.lejdi.planner.business.utils.date.DateFormatter
import pl.lejdi.planner.di.qualifiers.PreferencesDataStoreWrapperQualifier
import pl.lejdi.planner.framework.datasource.datastore.DataStoreWrapper
import pl.lejdi.planner.framework.datasource.datastore.PreferencesDataStoreWrapper
import pl.lejdi.planner.framework.datasource.datastore.plannerDatastore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.plannerDatastore
    }

    @Singleton
    @Provides
    @PreferencesDataStoreWrapperQualifier
    fun providePreferencesDataStoreWrapper(dataStore: DataStore<Preferences>) : DataStoreWrapper {
        return PreferencesDataStoreWrapper(dataStore)
    }

    @Singleton
    @Provides
    fun provideLastCacheCleanupDataStoreInteractor(
        @PreferencesDataStoreWrapperQualifier dataStoreWrapper: DataStoreWrapper,
        dateFormatter: DateFormatter
    ) : LastCacheCleanupDataStoreInteractor {
        return LastCacheCleanupDataStoreInteractor(dataStoreWrapper, dateFormatter)
    }
}