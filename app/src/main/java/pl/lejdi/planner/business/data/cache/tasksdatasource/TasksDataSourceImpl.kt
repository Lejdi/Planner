package pl.lejdi.planner.business.data.cache.tasksdatasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import pl.lejdi.planner.business.data.cache.util.CacheResult
import pl.lejdi.planner.business.data.cache.util.safeCacheCall
import pl.lejdi.planner.framework.datasource.cache.dao.TasksDao
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntity

class TasksDataSourceImpl(
    private val tasksDao: TasksDao,
) : TasksDataSource {
    override suspend fun getAllTasks(): Flow<CacheResult<List<TaskEntity>>> {
        return tasksDao.getAllTasks()
            .flowOn(Dispatchers.IO)
            .map {
               CacheResult.Success(it) as CacheResult<List<TaskEntity>>
            }
            .catch { emit(CacheResult.Error()) }
    }

    override suspend fun addTask(task: TaskEntity): CacheResult<Unit> {
        return safeCacheCall(Dispatchers.IO) {
            val result = tasksDao.insertTask(task)
            if (result < 0L) throw Exception()
        }
    }

    override suspend fun deleteTask(task: TaskEntity): CacheResult<Unit> {
        return safeCacheCall(Dispatchers.IO) {
            val result = tasksDao.deleteTask(task)
            if (result != 1) throw Exception()
        }
    }

    override suspend fun updateTask(task: TaskEntity): CacheResult<Unit> {
        return safeCacheCall(Dispatchers.IO) {
            val result = tasksDao.updateTask(task)
            if (result != 1) throw Exception()
        }
    }

}