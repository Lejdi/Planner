package pl.lejdi.planner.business.usecases.taskslist

import pl.lejdi.planner.business.data.cache.tasksdatasource.TasksDataSource
import pl.lejdi.planner.business.data.cache.utl.CacheResult
import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.business.usecases.UseCase
import pl.lejdi.planner.business.usecases.UseCaseResult
import pl.lejdi.planner.business.utils.date.addDays
import pl.lejdi.planner.business.utils.date.daysSinceDate
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntityMapper
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayable
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayableMapper
import pl.lejdi.planner.framework.presentation.util.ErrorType

class MarkTaskComplete(
    private val tasksDataSource: TasksDataSource,
    private val taskEntityMapper: TaskEntityMapper,
    val taskDisplayableMapper: TaskDisplayableMapper,
) : UseCase<UseCaseResult<Unit>, TaskDisplayable>() {

    override suspend fun execute(taskDisplayable: TaskDisplayable): UseCaseResult<Unit> {
        val cacheResult: CacheResult<Unit>
        val task = taskDisplayableMapper.mapToBusinessModel(taskDisplayable)

        //for non-periodic simply delete
        if (task.daysInterval == 0) {
            cacheResult = deleteTask(task)
        } else {
            val newStartDate = task.startDate.addDays(task.daysInterval)
            val daysFromStartToEnd = task.endDate.daysSinceDate(newStartDate)
            //the lass occurrence of periodic task - delete
            if (daysFromStartToEnd < 1) {
                cacheResult = deleteTask(task)
            }
            //for periodic non-finished task update start date to the next occurrence
            else {
                val updatedTask = task.copy(
                    startDate = newStartDate
                )
                cacheResult = updateTask(updatedTask)
            }
        }

        return if (cacheResult is CacheResult.Success) UseCaseResult.Success(Unit)
        else UseCaseResult.Error(ErrorType.CacheError)
    }

    private suspend fun deleteTask(task: Task): CacheResult<Unit> {
        return tasksDataSource.deleteTask(taskEntityMapper.mapFromBusinessModel(task))
    }

    private suspend fun updateTask(task: Task): CacheResult<Unit> {
        return tasksDataSource.updateTask(taskEntityMapper.mapFromBusinessModel(task))
    }
}