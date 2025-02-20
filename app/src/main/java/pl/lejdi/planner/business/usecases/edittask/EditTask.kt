package pl.lejdi.planner.business.usecases.edittask

import pl.lejdi.planner.business.data.cache.tasksdatasource.TasksDataSource
import pl.lejdi.planner.business.data.cache.util.CacheResult
import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.business.usecases.UseCase
import pl.lejdi.planner.business.usecases.UseCaseResult
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntityMapper
import pl.lejdi.planner.framework.presentation.util.ErrorType

class EditTask(
    private val tasksDataSource: TasksDataSource,
    private val taskEntityMapper: TaskEntityMapper,
) : UseCase<UseCaseResult<Unit>, Task>() {
    override suspend fun execute(task: Task): UseCaseResult<Unit> {
        val taskEntity = taskEntityMapper.mapFromBusinessModel(task)
        val cacheResult = tasksDataSource.updateTask(taskEntity)
        return if (cacheResult is CacheResult.Success) UseCaseResult.Success(Unit)
        else UseCaseResult.Error(ErrorType.CacheError)
    }
}