package pl.lejdi.planner.business.data.cache.tasksdatesource

import pl.lejdi.planner.business.data.cache.tasksdatasource.TasksDataSource
import pl.lejdi.planner.business.data.cache.util.CacheResult
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntity

object MockTasksDataSource : TasksDataSource {

    fun setupMockList(mockedTasks: List<TaskEntity>) {
        tasksList.addAll(mockedTasks)
    }

    val tasksList = mutableListOf<TaskEntity>()

    override suspend fun getAllTasks(): CacheResult<List<TaskEntity>> {
        return CacheResult.Success(tasksList)
    }

    override suspend fun addTask(task: TaskEntity): CacheResult<Unit> {
        tasksList.add(task)
        return CacheResult.Success(Unit)
    }

    override suspend fun deleteTask(task: TaskEntity): CacheResult<Unit> {
        tasksList.remove(task)
        return CacheResult.Success(Unit)
    }

    override suspend fun updateTask(task: TaskEntity): CacheResult<Unit> {
        val taskInList = tasksList.first { it.id == task.id }
        tasksList.remove(taskInList)
        tasksList.add(task)
        return CacheResult.Success(Unit)
    }
}