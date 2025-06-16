package pl.lejdi.planner.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pl.lejdi.planner.business.data.cache.datastore.LastCacheCleanupDataStoreInteractor
import pl.lejdi.planner.business.data.cache.grocery.GroceryItemDataSource
import pl.lejdi.planner.business.data.cache.tasksdatasource.TasksDataSource
import pl.lejdi.planner.business.usecases.edittask.AddTask
import pl.lejdi.planner.business.usecases.edittask.DeleteTask
import pl.lejdi.planner.business.usecases.edittask.EditTask
import pl.lejdi.planner.business.usecases.edittask.EditTaskUseCases
import pl.lejdi.planner.business.usecases.dashboard.UpdateTasksDates
import pl.lejdi.planner.business.usecases.dashboard.GetTasksForDashboard
import pl.lejdi.planner.business.usecases.dashboard.MarkTaskComplete
import pl.lejdi.planner.business.usecases.dashboard.DashboardUseCases
import pl.lejdi.planner.business.usecases.grocery.AddGrocery
import pl.lejdi.planner.business.usecases.grocery.DeleteGrocery
import pl.lejdi.planner.business.usecases.grocery.EditGrocery
import pl.lejdi.planner.business.usecases.grocery.GetAllGroceries
import pl.lejdi.planner.business.usecases.grocery.GroceryUseCases
import pl.lejdi.planner.business.utils.date.DateUtil
import pl.lejdi.planner.business.utils.date.format.DateFormatter
import pl.lejdi.planner.framework.datasource.cache.model.grocery.GroceryItemEntityMapper
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntityMapper
import pl.lejdi.planner.framework.presentation.common.model.grocery.GroceryDisplayableMapper
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
        taskEntityMapper: TaskEntityMapper
    ) : DeleteTask {
        return DeleteTask(
            tasksDataSource = tasksDataSource,
            taskEntityMapper = taskEntityMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideDeleteOutdatedTasksUseCase(
        tasksDataSource: TasksDataSource,
        taskEntityMapper: TaskEntityMapper,
        lastCacheCleanupDataStoreInteractor: LastCacheCleanupDataStoreInteractor,
        dateUtil: DateUtil
    ) : UpdateTasksDates {
        return UpdateTasksDates(
            tasksDataSource = tasksDataSource,
            taskEntityMapper = taskEntityMapper,
            lastCacheCleanupDataStoreInteractor = lastCacheCleanupDataStoreInteractor,
            dateUtil = dateUtil,
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
        taskDisplayableMapper: TaskDisplayableMapper,
        dateFormatter: DateFormatter,
        dateUtil: DateUtil
    ) : GetTasksForDashboard {
        return GetTasksForDashboard(
            tasksDataSource = tasksDataSource,
            taskEntityMapper = taskEntityMapper,
            taskDisplayableMapper = taskDisplayableMapper,
            dateFormatter = dateFormatter,
            dateUtil = dateUtil
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
    fun provideDashboardUseCases(
        updateTasksDates: UpdateTasksDates,
        markTaskComplete: MarkTaskComplete,
        getTasksForDashboard: GetTasksForDashboard
    ) : DashboardUseCases {
        return DashboardUseCases(
            updateTasksDates = updateTasksDates,
            markTaskComplete = markTaskComplete,
            getTasksForDashboard = getTasksForDashboard,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideAddGroceryUseCase(
        groceryItemDataSource: GroceryItemDataSource,
        groceryItemEntityMapper: GroceryItemEntityMapper,
    ) : AddGrocery {
        return AddGrocery(
            groceryItemDataSource = groceryItemDataSource,
            groceryItemEntityMapper = groceryItemEntityMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideEditGroceryUseCase(
        groceryItemDataSource: GroceryItemDataSource,
        groceryItemEntityMapper: GroceryItemEntityMapper,
        groceryDisplayableMapper: GroceryDisplayableMapper,
    ) : EditGrocery {
        return EditGrocery(
            groceryItemDataSource = groceryItemDataSource,
            groceryItemEntityMapper = groceryItemEntityMapper,
            groceryDisplayableMapper = groceryDisplayableMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideDeleteGroceryUseCase(
        groceryItemDataSource: GroceryItemDataSource,
        groceryItemEntityMapper: GroceryItemEntityMapper,
        groceryDisplayableMapper: GroceryDisplayableMapper,
    ) : DeleteGrocery {
        return DeleteGrocery(
            groceryItemDataSource = groceryItemDataSource,
            groceryItemEntityMapper = groceryItemEntityMapper,
            groceryDisplayableMapper = groceryDisplayableMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetAllGroceriesUseCase(
        groceryItemDataSource: GroceryItemDataSource,
        groceryItemEntityMapper: GroceryItemEntityMapper,
        groceryDisplayableMapper: GroceryDisplayableMapper,
    ) : GetAllGroceries {
        return GetAllGroceries(
            groceryItemDataSource = groceryItemDataSource,
            groceryItemEntityMapper = groceryItemEntityMapper,
            groceryDisplayableMapper = groceryDisplayableMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGroceryUseCases(
        addGrocery: AddGrocery,
        deleteGrocery: DeleteGrocery,
        editGrocery: EditGrocery,
        getAllGroceries: GetAllGroceries
    ) : GroceryUseCases {
        return GroceryUseCases(
            addGrocery = addGrocery,
            deleteGrocery = deleteGrocery,
            editGrocery = editGrocery,
            getAllGroceries = getAllGroceries
        )
    }
}