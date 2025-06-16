package pl.lejdi.planner.business.usecases.grocery

import pl.lejdi.planner.business.data.cache.grocery.GroceryItemDataSource
import pl.lejdi.planner.business.data.cache.util.CacheResult
import pl.lejdi.planner.business.usecases.UseCase
import pl.lejdi.planner.business.usecases.UseCaseResult
import pl.lejdi.planner.framework.datasource.cache.model.grocery.GroceryItemEntityMapper
import pl.lejdi.planner.framework.presentation.common.model.grocery.GroceryDisplayable
import pl.lejdi.planner.framework.presentation.common.model.grocery.GroceryDisplayableMapper
import pl.lejdi.planner.framework.presentation.util.ErrorType

class DeleteGrocery(
    private val groceryItemDataSource: GroceryItemDataSource,
    private val groceryItemEntityMapper: GroceryItemEntityMapper,
    private val groceryDisplayableMapper: GroceryDisplayableMapper,
) : UseCase<UseCaseResult<Unit>, GroceryDisplayable>() {
    override suspend fun execute(item: GroceryDisplayable): UseCaseResult<Unit> {
        val itemEntity = groceryItemEntityMapper.mapFromBusinessModel(
            groceryDisplayableMapper.mapToBusinessModel(item)
        )
        val cacheResult = groceryItemDataSource.deleteGroceryItem(itemEntity)
        return if (cacheResult is CacheResult.Success) UseCaseResult.Success(Unit)
        else UseCaseResult.Error(ErrorType.CacheError)
    }
}