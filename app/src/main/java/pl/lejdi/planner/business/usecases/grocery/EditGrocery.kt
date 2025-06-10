package pl.lejdi.planner.business.usecases.grocery

import pl.lejdi.planner.business.data.cache.grocery.GroceryItemDataSource
import pl.lejdi.planner.business.data.cache.util.CacheResult
import pl.lejdi.planner.business.data.model.GroceryItem
import pl.lejdi.planner.business.usecases.UseCase
import pl.lejdi.planner.business.usecases.UseCaseResult
import pl.lejdi.planner.framework.datasource.cache.model.grocery.GroceryItemEntityMapper
import pl.lejdi.planner.framework.presentation.util.ErrorType

class EditGrocery(
    private val groceryItemDataSource: GroceryItemDataSource,
    private val groceryItemEntityMapper: GroceryItemEntityMapper,
) : UseCase<UseCaseResult<Unit>, GroceryItem>() {
    override suspend fun execute(item: GroceryItem): UseCaseResult<Unit> {
        val itemEntity = groceryItemEntityMapper.mapFromBusinessModel(item)
        val cacheResult = groceryItemDataSource.updateGroceryItem(itemEntity)
        return if (cacheResult is CacheResult.Success) UseCaseResult.Success(Unit)
        else UseCaseResult.Error(ErrorType.CacheError)
    }
}