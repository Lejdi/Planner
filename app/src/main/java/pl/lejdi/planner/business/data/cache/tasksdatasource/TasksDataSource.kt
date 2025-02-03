package pl.lejdi.planner.business.data.cache.tasksdatasource

interface TasksDataSource {

    fun getAllTasks()
    fun getTaskById()
    fun updateTask()
    fun deleteTask()
}