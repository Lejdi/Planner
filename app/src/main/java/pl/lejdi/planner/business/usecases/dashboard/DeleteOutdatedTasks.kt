package pl.lejdi.planner.business.usecases.dashboard

import pl.lejdi.planner.business.data.cache.datastore.LastCacheCleanupDataStoreInteractor
import pl.lejdi.planner.business.data.cache.tasksdatasource.TasksDataSource
import pl.lejdi.planner.business.data.cache.util.CacheResult
import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.business.usecases.UseCase
import pl.lejdi.planner.business.usecases.UseCaseResult
import pl.lejdi.planner.business.utils.date.daysSinceDate
import pl.lejdi.planner.business.utils.date.today
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntityMapper
import pl.lejdi.planner.framework.presentation.util.ErrorType

class DeleteOutdatedTasks(
    private val tasksDataSource: TasksDataSource,
    private val taskEntityMapper: TaskEntityMapper,
    private val lastCacheCleanupDataStoreInteractor: LastCacheCleanupDataStoreInteractor
) : UseCase<UseCaseResult<Unit>, Unit>()  {
    override suspend fun execute(params: Unit): UseCaseResult<Unit> {
        if(!shouldPerformCleanup()) return UseCaseResult.Success(Unit)

        //errors just to mark that cleanup wasn't completed
        var anyCacheIssuesFound = false
        val getTasksResult = tasksDataSource.getAllTasks()
        if(getTasksResult is CacheResult.Success){
            val cachedTasks = getTasksResult.data
            val tasksList = taskEntityMapper.mapListToBusinessModel(cachedTasks)
            val tasksToDelete = tasksList.filter { task -> shouldTaskBeDeleted(task) }
            taskEntityMapper.mapListFromBusinessModel(tasksToDelete).forEach { taskEntity ->
                val result = tasksDataSource.deleteTask(taskEntity)
                if(result is CacheResult.Error) anyCacheIssuesFound = true
            }
        }
        else {
            anyCacheIssuesFound = true
        }
        return if(anyCacheIssuesFound) UseCaseResult.Error(ErrorType.CacheError)
        else {
            updateCleanupDate()
            UseCaseResult.Success(Unit)
        }
    }

    private fun shouldTaskBeDeleted(task: Task) : Boolean{
        if(!task.asap){ //asap tasks always visible
            val currentDate = today()
            val daysFromTheEnd = currentDate.daysSinceDate(task.endDate)
            val daysFromTheStart = currentDate.daysSinceDate(task.startDate)
            val oneTimeTaskInPastDate = task.daysInterval == 0 && daysFromTheStart > 0
            val finishedPeriodicTask = task.daysInterval > 0 && daysFromTheEnd > 0
            if(oneTimeTaskInPastDate || finishedPeriodicTask){
                return true
            }
        }
        return false
    }

    private suspend fun shouldPerformCleanup() : Boolean {
        val lastCleanUpDate = lastCacheCleanupDataStoreInteractor.getData()
        val currentDate = today()
        val daysFromTheLastCleanup = currentDate.daysSinceDate(lastCleanUpDate)
        return daysFromTheLastCleanup > 7
    }

    private suspend fun updateCleanupDate() {
        val currentDate = today()
        lastCacheCleanupDataStoreInteractor.setData(currentDate)
    }
}