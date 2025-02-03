package pl.lejdi.planner.business.data.util

abstract class ModelMapper<BusinessModel, FrameworkModel> {
    abstract fun mapToBusinessModel(frameworkModel: FrameworkModel) : BusinessModel
    abstract fun mapFromBusinessModel(businessModel: BusinessModel) : FrameworkModel

    fun mapListFromBusinessModel(businessModelList: List<BusinessModel>): List<FrameworkModel> {
        return businessModelList.map { mapFromBusinessModel(it) }
    }

    fun mapListToBusinessModel(frameworkModelList: List<FrameworkModel>): List<BusinessModel> {
        return frameworkModelList.map { mapToBusinessModel(it) }
    }
}