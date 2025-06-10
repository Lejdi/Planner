package pl.lejdi.planner.framework.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.lejdi.planner.framework.datasource.cache.dao.GroceryDao
import pl.lejdi.planner.framework.datasource.cache.dao.TasksDao
import pl.lejdi.planner.framework.datasource.cache.model.grocery.GroceryItemEntity
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntity

@Database(
    entities = [
        TaskEntity::class,
        GroceryItemEntity::class,
    ],
    exportSchema = true,
    version = 1
)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun getTasksDao(): TasksDao
    abstract fun getGroceryDao() : GroceryDao

    companion object {
        const val DB_NAME = "cache_db"
    }
}