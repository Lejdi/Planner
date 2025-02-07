package pl.lejdi.planner.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.lejdi.planner.business.utils.date.DateFormatter
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayableMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Singleton
    @Provides
    fun provideTaskDisplayableMapper(dateFormatter: DateFormatter) : TaskDisplayableMapper {
        return TaskDisplayableMapper(dateFormatter)
    }
}