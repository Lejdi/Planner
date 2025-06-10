package pl.lejdi.planner.framework.datasource.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pl.lejdi.planner.framework.datasource.cache.model.grocery.GroceryItemEntity

@Dao
interface GroceryDao {
    @Query("SELECT * FROM grocery_items")
    @Throws(Exception::class)
    fun getAllGroceryItems(): Flow<List<GroceryItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Throws(Exception::class)
    suspend fun insertGroceryItem(item: GroceryItemEntity): Long

    @Update
    @Throws(Exception::class)
    suspend fun updateGroceryItem(item: GroceryItemEntity): Int

    @Delete
    @Throws(Exception::class)
    suspend fun deleteGroceryItem(item: GroceryItemEntity): Int
}