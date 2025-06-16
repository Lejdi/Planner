package pl.lejdi.planner.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import pl.lejdi.planner.business.data.cache.grocery.GroceryItemDataSource
import pl.lejdi.planner.business.data.cache.grocerydatasource.MockGroceryDataSource
import pl.lejdi.planner.business.data.cache.tasksdatasource.TasksDataSource
import pl.lejdi.planner.business.data.cache.tasksdatesource.MockTasksDataSource
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CacheModule::class]
)
object MockCacheModule {

    @Singleton
    @Provides
    fun provideTasksDataSource() : TasksDataSource {
        return MockTasksDataSource
    }

    @Singleton
    @Provides
    fun provideGroceryItemDataSource() : GroceryItemDataSource {
        return MockGroceryDataSource
    }
}