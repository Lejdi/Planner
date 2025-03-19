package pl.lejdi.planner.business.usecases.dashboard

import pl.lejdi.planner.business.data.cache.datastore.LastCacheCleanupDataStoreInteractor
import pl.lejdi.planner.business.data.cache.tasksdatasource.TasksDataSource
import pl.lejdi.planner.business.data.cache.util.CacheResult
import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.business.usecases.UseCase
import pl.lejdi.planner.business.usecases.UseCaseResult
import pl.lejdi.planner.business.utils.date.DateUtil
import pl.lejdi.planner.business.utils.date.daysSinceDate
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntityMapper

class DeleteOutdatedTasks(
    private val tasksDataSource: TasksDataSource,
    private val taskEntityMapper: TaskEntityMapper,
    private val lastCacheCleanupDataStoreInteractor: LastCacheCleanupDataStoreInteractor,
    private val dateUtil: DateUtil,
) : UseCase<UseCaseResult<Unit>, Unit>() {
    override suspend fun execute(params: Unit): UseCaseResult<Unit> {
        if (!shouldPerformCleanup()) return UseCaseResult.Success(Unit)

        //errors just to mark that cleanup wasn't completed
        var anyCacheIssuesFound = false
        tasksDataSource.getAllTasks().collect {
            if (it is CacheResult.Success) {
                val cachedTasks = it.data
                val tasksList = taskEntityMapper.mapListToBusinessModel(cachedTasks)
                val tasksToDelete = tasksList.filter { task -> shouldTaskBeDeleted(task) }
                taskEntityMapper.mapListFromBusinessModel(tasksToDelete).forEach { taskEntity ->
                    val result = tasksDataSource.deleteTask(taskEntity)
                    if (result is CacheResult.Error) anyCacheIssuesFound = true
                }
            } else {
                anyCacheIssuesFound = true
            }
            if(!anyCacheIssuesFound){
                updateCleanupDate()
            }
        }

        return UseCaseResult.Success(Unit)
    }

    private fun shouldTaskBeDeleted(task: Task): Boolean {
        if (!task.asap) { //asap tasks always visible
            val currentDate = dateUtil.getToday()
            val daysFromTheEnd = task.endDate.daysSinceDate(currentDate)
            val daysFromTheStart = task.startDate.daysSinceDate(currentDate)
            val oneTimeTaskInPastDate = task.daysInterval == 0 && daysFromTheStart > 0
            val finishedPeriodicTask = task.daysInterval > 0 && daysFromTheEnd > 0
            if (oneTimeTaskInPastDate || finishedPeriodicTask) {
                return true
            }
        }
        return false
    }

    private suspend fun shouldPerformCleanup(): Boolean {
        val lastCleanUpDate = lastCacheCleanupDataStoreInteractor.getData()
        val currentDate = dateUtil.getToday()
        val daysFromTheLastCleanup = lastCleanUpDate.daysSinceDate(currentDate)
        return daysFromTheLastCleanup > 7
    }

    private suspend fun updateCleanupDate() {
        val currentDate = dateUtil.getToday()
        lastCacheCleanupDataStoreInteractor.setData(currentDate)
    }
}