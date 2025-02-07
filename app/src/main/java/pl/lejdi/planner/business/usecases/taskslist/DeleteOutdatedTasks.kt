package pl.lejdi.planner.business.usecases.taskslist

import pl.lejdi.planner.business.data.cache.tasksdatasource.TasksDataSource
import pl.lejdi.planner.business.data.cache.utl.CacheResult
import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.business.usecases.UseCase
import pl.lejdi.planner.business.usecases.UseCaseResult
import pl.lejdi.planner.business.utils.date.daysSinceDate
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntityMapper
import pl.lejdi.planner.framework.presentation.util.ErrorType
import java.util.Date

class DeleteOutdatedTasks(
    private val tasksDataSource: TasksDataSource,
    private val taskEntityMapper: TaskEntityMapper,
) : UseCase<UseCaseResult<Unit>, Unit>()  {
    override suspend fun execute(params: Unit): UseCaseResult<Unit> {
        //errors just to mark that cleanup wasn't completed
        var anyCacheIssuesFound = false
        val getTasksResult = tasksDataSource.getAllTasks()
        if(getTasksResult is CacheResult.Success){
            val tasksToDelete = mutableListOf<Task>()
            val cachedTasks = getTasksResult.data
            val tasksList = taskEntityMapper.mapListToBusinessModel(cachedTasks)
            tasksList.forEach { task ->
                if(!task.asap){ //asap tasks always visible
                    val currentDate = Date()
                    val daysFromTheEnd = currentDate.daysSinceDate(task.endDate)
                    val daysFromTheStart = currentDate.daysSinceDate(task.startDate)
                    val oneTimeTaskInPastDate = task.daysInterval == 0 && daysFromTheStart > 0
                    val finishedPeriodicTask = task.daysInterval > 0 && daysFromTheEnd > 0
                    if(oneTimeTaskInPastDate || finishedPeriodicTask){
                        tasksToDelete.add(task)
                    }
                }
            }
            taskEntityMapper.mapListFromBusinessModel(tasksToDelete).forEach { taskEntity ->
                val result = tasksDataSource.deleteTask(taskEntity)
                if(result is CacheResult.Error) anyCacheIssuesFound = true
            }
        }
        else {
            anyCacheIssuesFound = true
        }
        return if(anyCacheIssuesFound) UseCaseResult.Error(ErrorType.CacheError)
        else UseCaseResult.Success(Unit)
    }
}