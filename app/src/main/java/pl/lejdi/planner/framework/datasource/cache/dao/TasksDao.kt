package pl.lejdi.planner.framework.datasource.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntity

@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks")
    @Throws(Exception::class)
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Throws(Exception::class)
    suspend fun insertTask(task: TaskEntity): Long

    @Update
    @Throws(Exception::class)
    suspend fun updateTask(task: TaskEntity): Int

    @Delete
    @Throws(Exception::class)
    suspend fun deleteTask(task: TaskEntity): Int
}