package pl.lejdi.planner.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.lejdi.planner.business.data.cache.grocery.GroceryItemDataSource
import pl.lejdi.planner.business.data.cache.grocery.GroceryItemDataSourceImpl
import pl.lejdi.planner.business.data.cache.tasksdatasource.TasksDataSource
import pl.lejdi.planner.business.data.cache.tasksdatasource.TasksDataSourceImpl
import pl.lejdi.planner.framework.datasource.cache.CacheDatabase
import pl.lejdi.planner.framework.datasource.cache.dao.GroceryDao
import pl.lejdi.planner.framework.datasource.cache.dao.TasksDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CacheDatabase {
        return Room.databaseBuilder(
            context,
            CacheDatabase::class.java,
            CacheDatabase.DB_NAME
        )
            .build()
    }

    @Singleton
    @Provides
    fun provideTasksDao(cacheDatabase: CacheDatabase): TasksDao {
        return cacheDatabase.getTasksDao()
    }

    @Singleton
    @Provides
    fun provideGroceryDao(cacheDatabase: CacheDatabase) : GroceryDao {
        return cacheDatabase.getGroceryDao()
    }

    @Singleton
    @Provides
    fun provideTasksDataSource(tasksDao: TasksDao) : TasksDataSource {
        return TasksDataSourceImpl(tasksDao)
    }

    @Singleton
    @Provides
    fun provideGroceryItemDataSource(groceryDao: GroceryDao) : GroceryItemDataSource {
        return GroceryItemDataSourceImpl(groceryDao)
    }
}