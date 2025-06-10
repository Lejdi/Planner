package pl.lejdi.planner.business.data.cache.grocery

import kotlinx.coroutines.flow.Flow
import pl.lejdi.planner.business.data.cache.util.CacheResult
import pl.lejdi.planner.framework.datasource.cache.model.grocery.GroceryItemEntity

interface GroceryItemDataSource {

    suspend fun getAllGroceryItems(): Flow<CacheResult<List<GroceryItemEntity>>>
    suspend fun addGroceryItem(item: GroceryItemEntity): CacheResult<Unit>
    suspend fun deleteGroceryItem(item: GroceryItemEntity): CacheResult<Unit>
    suspend fun updateGroceryItem(item: GroceryItemEntity): CacheResult<Unit>
}