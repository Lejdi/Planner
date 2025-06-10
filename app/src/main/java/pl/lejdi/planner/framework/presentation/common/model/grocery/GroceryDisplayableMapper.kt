package pl.lejdi.planner.framework.presentation.common.model.grocery

import pl.lejdi.planner.business.data.model.GroceryItem
import pl.lejdi.planner.business.data.util.ModelMapper

class GroceryDisplayableMapper: ModelMapper<GroceryItem, GroceryDisplayable>()  {
    override fun mapToBusinessModel(frameworkModel: GroceryDisplayable): GroceryItem {
        return GroceryItem(
            id = frameworkModel.id,
            name = frameworkModel.name,
            description = frameworkModel.description
        )
    }

    override fun mapFromBusinessModel(businessModel: GroceryItem): GroceryDisplayable {
        return GroceryDisplayable(
            id = businessModel.id,
            name = businessModel.name,
            description = businessModel.description
        )
    }
}