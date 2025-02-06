package pl.lejdi.planner.business.data.cache.tasksdatasource

import kotlinx.coroutines.Dispatchers
import pl.lejdi.planner.business.data.cache.utl.CacheResult
import pl.lejdi.planner.business.data.cache.utl.safeCacheCall
import pl.lejdi.planner.framework.datasource.cache.dao.TasksDao
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntity

class TasksDataSourceImpl(
    private val tasksDao: TasksDao
) : TasksDataSource {
    override suspend fun getAllTasks(): CacheResult<List<TaskEntity>> {
        return safeCacheCall(Dispatchers.IO) {
            tasksDao.getAllTasks()
        }
    }

    override suspend fun addTask(task: TaskEntity): CacheResult<Unit> {
        return safeCacheCall(Dispatchers.IO) {
            tasksDao.insertTask(task)
        }
    }

    override suspend fun deleteTask(task: TaskEntity): CacheResult<Unit> {
        return safeCacheCall(Dispatchers.IO) {
            tasksDao.deleteTask(task)
        }
    }

    override suspend fun updateTask(task: TaskEntity): CacheResult<Unit> {
        return safeCacheCall(Dispatchers.IO) {
            tasksDao.updateTask(task)
        }
    }

}