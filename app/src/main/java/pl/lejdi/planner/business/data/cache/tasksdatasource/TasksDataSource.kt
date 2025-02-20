package pl.lejdi.planner.business.data.cache.tasksdatasource

import pl.lejdi.planner.business.data.cache.util.CacheResult
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntity

interface TasksDataSource {

    suspend fun getAllTasks(): CacheResult<List<TaskEntity>>
    suspend fun addTask(task: TaskEntity): CacheResult<Unit>
    suspend fun deleteTask(task: TaskEntity): CacheResult<Unit>
    suspend fun updateTask(task: TaskEntity): CacheResult<Unit>
}