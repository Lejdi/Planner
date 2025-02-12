package pl.lejdi.planner.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pl.lejdi.planner.business.data.cache.tasksdatasource.TasksDataSource
import pl.lejdi.planner.business.usecases.edittask.AddTask
import pl.lejdi.planner.business.usecases.edittask.DeleteTask
import pl.lejdi.planner.business.usecases.edittask.EditTask
import pl.lejdi.planner.business.usecases.edittask.EditTaskUseCases
import pl.lejdi.planner.business.usecases.taskslist.DeleteOutdatedTasks
import pl.lejdi.planner.business.usecases.taskslist.GetTasksForDashboard
import pl.lejdi.planner.business.usecases.taskslist.MarkTaskComplete
import pl.lejdi.planner.business.usecases.taskslist.TasksListUseCases
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntityMapper
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayableMapper


@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @ViewModelScoped
    @Provides
    fun provideAddTaskUseCase(
        tasksDataSource: TasksDataSource,
        taskEntityMapper: TaskEntityMapper,
    ) : AddTask {
        return AddTask(
            tasksDataSource = tasksDataSource,
            taskEntityMapper = taskEntityMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideEditTaskUseCase(
        tasksDataSource: TasksDataSource,
        taskEntityMapper: TaskEntityMapper,
    ) : EditTask {
        return EditTask(
            tasksDataSource = tasksDataSource,
            taskEntityMapper = taskEntityMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideDeleteTaskUseCase(
        tasksDataSource: TasksDataSource,
        taskEntityMapper: TaskEntityMapper,
        taskDisplayableMapper: TaskDisplayableMapper
    ) : DeleteTask {
        return DeleteTask(
            tasksDataSource = tasksDataSource,
            taskEntityMapper = taskEntityMapper,
            taskDisplayableMapper = taskDisplayableMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideDeleteOutdatedTasksUseCase(
        tasksDataSource: TasksDataSource,
        taskEntityMapper: TaskEntityMapper,
        taskDisplayableMapper: TaskDisplayableMapper
    ) : DeleteOutdatedTasks {
        return DeleteOutdatedTasks(
            tasksDataSource = tasksDataSource,
            taskEntityMapper = taskEntityMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideMarkTaskCompletedUseCase(
        tasksDataSource: TasksDataSource,
        taskEntityMapper: TaskEntityMapper,
        taskDisplayableMapper: TaskDisplayableMapper
    ) : MarkTaskComplete {
        return MarkTaskComplete(
            tasksDataSource = tasksDataSource,
            taskEntityMapper = taskEntityMapper,
            taskDisplayableMapper = taskDisplayableMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetTasksForDashboardUseCase(
        tasksDataSource: TasksDataSource,
        taskEntityMapper: TaskEntityMapper,
        taskDisplayableMapper: TaskDisplayableMapper
    ) : GetTasksForDashboard {
        return GetTasksForDashboard(
            tasksDataSource = tasksDataSource,
            taskEntityMapper = taskEntityMapper,
            taskDisplayableMapper = taskDisplayableMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideEditTaskUseCases(
        addTask: AddTask,
        editTask: EditTask,
        deleteTask: DeleteTask
    ) : EditTaskUseCases {
        return EditTaskUseCases(
            addTask = addTask,
            editTask = editTask,
            deleteTask = deleteTask
        )
    }

    @ViewModelScoped
    @Provides
    fun provideTasksListUseCases(
        deleteOutdatedTasks: DeleteOutdatedTasks,
        markTaskComplete: MarkTaskComplete,
        getTasksForDashboard: GetTasksForDashboard
    ) : TasksListUseCases {
        return TasksListUseCases(
            deleteOutdatedTasks = deleteOutdatedTasks,
            markTaskComplete = markTaskComplete,
            getTasksForDashboard = getTasksForDashboard,
        )
    }
}