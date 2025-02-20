package pl.lejdi.planner.business.usecases.edittask

import pl.lejdi.planner.business.data.cache.tasksdatasource.TasksDataSource
import pl.lejdi.planner.business.data.cache.util.CacheResult
import pl.lejdi.planner.business.usecases.UseCase
import pl.lejdi.planner.business.usecases.UseCaseResult
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntityMapper
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayable
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayableMapper
import pl.lejdi.planner.framework.presentation.util.ErrorType

class DeleteTask(
    private val tasksDataSource: TasksDataSource,
    private val taskEntityMapper: TaskEntityMapper,
    private val taskDisplayableMapper: TaskDisplayableMapper,
) : UseCase<UseCaseResult<Unit>, TaskDisplayable>() {
    override suspend fun execute(taskToDelete: TaskDisplayable): UseCaseResult<Unit> {
        val taskEntity = taskEntityMapper.mapFromBusinessModel(taskDisplayableMapper.mapToBusinessModel(taskToDelete))
        val cacheResult = tasksDataSource.deleteTask(taskEntity)
        return if (cacheResult is CacheResult.Success) UseCaseResult.Success(Unit)
        else UseCaseResult.Error(ErrorType.CacheError)
    }
}