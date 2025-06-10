package pl.lejdi.planner.business.data.cache.grocery

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import pl.lejdi.planner.business.data.cache.util.CacheResult
import pl.lejdi.planner.business.data.cache.util.safeCacheCall
import pl.lejdi.planner.framework.datasource.cache.dao.GroceryDao
import pl.lejdi.planner.framework.datasource.cache.model.grocery.GroceryItemEntity

class GroceryItemDataSourceImpl(
    private val groceryDao: GroceryDao
) : GroceryItemDataSource {
    override suspend fun getAllGroceryItems(): Flow<CacheResult<List<GroceryItemEntity>>> {
        return groceryDao.getAllGroceryItems()
            .flowOn(Dispatchers.IO)
            .map {
                CacheResult.Success(it) as CacheResult<List<GroceryItemEntity>>
            }
            .catch { emit(CacheResult.Error()) }
    }

    override suspend fun addGroceryItem(item: GroceryItemEntity): CacheResult<Unit> {
        return safeCacheCall(Dispatchers.IO) {
            val result = groceryDao.insertGroceryItem(item)
            if (result < 0L) throw Exception()
        }
    }

    override suspend fun deleteGroceryItem(item: GroceryItemEntity): CacheResult<Unit> {
        return safeCacheCall(Dispatchers.IO) {
            val result = groceryDao.deleteGroceryItem(item)
            if (result != 1) throw Exception()
        }
    }

    override suspend fun updateGroceryItem(item: GroceryItemEntity): CacheResult<Unit> {
        return safeCacheCall(Dispatchers.IO) {
            val result = groceryDao.updateGroceryItem(item)
            if (result != 1) throw Exception()
        }
    }
}