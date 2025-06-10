package pl.lejdi.planner.business.usecases.grocery

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.lejdi.planner.business.data.cache.grocery.GroceryItemDataSource
import pl.lejdi.planner.business.data.cache.util.CacheResult
import pl.lejdi.planner.business.usecases.UseCase
import pl.lejdi.planner.business.usecases.UseCaseResult
import pl.lejdi.planner.framework.datasource.cache.model.grocery.GroceryItemEntityMapper
import pl.lejdi.planner.framework.presentation.common.model.grocery.GroceryDisplayable
import pl.lejdi.planner.framework.presentation.common.model.grocery.GroceryDisplayableMapper
import pl.lejdi.planner.framework.presentation.util.ErrorType

class GetAllGroceries(
    private val groceryItemDataSource: GroceryItemDataSource,
    private val groceryItemEntityMapper: GroceryItemEntityMapper,
    private val groceryDisplayableMapper: GroceryDisplayableMapper,
) : UseCase<Flow<UseCaseResult<List<GroceryDisplayable>>>, Unit>() {
    override suspend fun execute(params: Unit): Flow<UseCaseResult<List<GroceryDisplayable>>> {
        return groceryItemDataSource.getAllGroceryItems().map { result ->
            if (result is CacheResult.Error) {
                UseCaseResult.Error(ErrorType.CacheError)
            }
            else {
                val cacheGroceryItemsList = (result as CacheResult.Success).data
                val groceryList = groceryItemEntityMapper.mapListToBusinessModel(cacheGroceryItemsList)
                val resultList = groceryDisplayableMapper.mapListFromBusinessModel(groceryList)
                UseCaseResult.Success(resultList)
            }
        }
    }
}