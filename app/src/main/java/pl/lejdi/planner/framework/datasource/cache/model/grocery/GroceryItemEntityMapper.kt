package pl.lejdi.planner.framework.datasource.cache.model.grocery

import pl.lejdi.planner.business.data.model.GroceryItem
import pl.lejdi.planner.business.data.util.ModelMapper


class GroceryItemEntityMapper : ModelMapper<GroceryItem, GroceryItemEntity>() {

    override fun mapFromBusinessModel(businessModel: GroceryItem): GroceryItemEntity {
        return GroceryItemEntity(
            id = businessModel.id,
            name = businessModel.name,
            description = businessModel.description
        )
    }

    override fun mapToBusinessModel(frameworkModel: GroceryItemEntity): GroceryItem {
        return GroceryItem(
            id = frameworkModel.id,
            name = frameworkModel.name,
            description = frameworkModel.description
        )
    }
}