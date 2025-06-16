package pl.lejdi.planner.business.data.cache.grocerydatasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pl.lejdi.planner.business.data.cache.grocery.GroceryItemDataSource
import pl.lejdi.planner.business.data.cache.util.CacheResult
import pl.lejdi.planner.framework.datasource.cache.model.grocery.GroceryItemEntity

object MockGroceryDataSource : GroceryItemDataSource {
    override suspend fun getAllGroceryItems(): Flow<CacheResult<List<GroceryItemEntity>>> {
        return MutableStateFlow(CacheResult.Success(emptyList()))
    }

    override suspend fun addGroceryItem(item: GroceryItemEntity): CacheResult<Unit> {
        return CacheResult.Success(Unit)
    }

    override suspend fun deleteGroceryItem(item: GroceryItemEntity): CacheResult<Unit> {
        return CacheResult.Success(Unit)
    }

    override suspend fun updateGroceryItem(item: GroceryItemEntity): CacheResult<Unit> {
        return CacheResult.Success(Unit)
    }
}