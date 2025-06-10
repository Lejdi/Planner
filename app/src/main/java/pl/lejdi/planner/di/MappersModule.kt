package pl.lejdi.planner.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.lejdi.planner.business.utils.date.format.DateFormatter
import pl.lejdi.planner.framework.datasource.cache.model.grocery.GroceryItemEntityMapper
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntityMapper
import pl.lejdi.planner.framework.presentation.common.model.grocery.GroceryDisplayableMapper
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayableMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MappersModule {

    @Singleton
    @Provides
    fun provideCacheTaskMapper(dateFormatter: DateFormatter) : TaskEntityMapper {
        return TaskEntityMapper(dateFormatter)
    }

    @Singleton
    @Provides
    fun provideTaskDisplayableMapper(dateFormatter: DateFormatter) : TaskDisplayableMapper {
        return TaskDisplayableMapper(dateFormatter)
    }

    @Singleton
    @Provides
    fun provideCacheGroceryMapper() : GroceryItemEntityMapper {
        return GroceryItemEntityMapper()
    }

    @Singleton
    @Provides
    fun provideDisplayableGroceryMapper() : GroceryDisplayableMapper {
        return GroceryDisplayableMapper()
    }
}