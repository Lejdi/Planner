package pl.lejdi.planner.business.data.cache.tasksdatesource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import pl.lejdi.planner.business.data.cache.tasksdatasource.TasksDataSource
import pl.lejdi.planner.business.data.cache.util.CacheResult
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntity

object MockTasksDataSource : TasksDataSource {

    private val listChangesFlow = MutableStateFlow(0)

    fun setupMockList(mockedTasks: List<TaskEntity>) {
        tasksList.addAll(mockedTasks)
    }

    fun clearList(){
        tasksList.clear()
    }

    fun peekTasks() : List<TaskEntity> = tasksList

    private val tasksList = mutableListOf<TaskEntity>()

    override suspend fun getAllTasks(): Flow<CacheResult<List<TaskEntity>>> = flow {
        listChangesFlow.onEach {
            emit(CacheResult.Success(tasksList))
        }.collect()
    }

    override suspend fun addTask(task: TaskEntity): CacheResult<Unit> {
        tasksList.add(task)
        listChangesFlow.value += 1
        return CacheResult.Success(Unit)
    }

    override suspend fun deleteTask(task: TaskEntity): CacheResult<Unit> {
        tasksList.remove(task)
        listChangesFlow.value += 1
        return CacheResult.Success(Unit)
    }

    override suspend fun updateTask(task: TaskEntity): CacheResult<Unit> {
        val taskInList = tasksList.first { it.id == task.id }
        tasksList.remove(taskInList)
        tasksList.add(task)
        listChangesFlow.value += 1
        return CacheResult.Success(Unit)
    }
}