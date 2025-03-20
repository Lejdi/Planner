package pl.lejdi.planner.business.data.cache.tasksdatesource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pl.lejdi.planner.business.data.cache.tasksdatasource.TasksDataSource
import pl.lejdi.planner.business.data.cache.util.CacheResult
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntity

object MockTasksDataSource : TasksDataSource {

    private lateinit var tasksListStateFlow : MutableStateFlow<CacheResult<List<TaskEntity>>>

    fun setupMockList(mockedTasks: List<TaskEntity>) {
        tasksList.addAll(mockedTasks)
        tasksListStateFlow = MutableStateFlow(CacheResult.Success(tasksList))
    }

    fun clearList(){
        tasksList.clear()
    }

    fun peekTasks() : List<TaskEntity> = tasksList

    private var tasksList = mutableListOf<TaskEntity>()

    private fun updateTasksFlow() {
        tasksListStateFlow.value = CacheResult.Success(tasksList)
    }

    override suspend fun getAllTasks(): Flow<CacheResult<List<TaskEntity>>> {
        return tasksListStateFlow
    }

    override suspend fun addTask(task: TaskEntity): CacheResult<Unit> {
        val newList = mutableListOf<TaskEntity>()
        newList.addAll(tasksList)
        newList.add(task)
        tasksList = newList
        updateTasksFlow()
        return CacheResult.Success(Unit)
    }

    override suspend fun deleteTask(task: TaskEntity): CacheResult<Unit> {
        val newList = mutableListOf<TaskEntity>()
        newList.addAll(tasksList.filter { it != task })
        tasksList = newList
        updateTasksFlow()
        return CacheResult.Success(Unit)
    }

    override suspend fun updateTask(task: TaskEntity): CacheResult<Unit> {
        val newList = mutableListOf<TaskEntity>()
        val taskToDelete = tasksList.first { it.id == task.id }
        newList.addAll(tasksList.filter { it != taskToDelete })
        newList.add(task)
        tasksList = newList
        updateTasksFlow()
        return CacheResult.Success(Unit)
    }
}