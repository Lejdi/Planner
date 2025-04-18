package pl.lejdi.planner.framework.datasource.cache.model.task

import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.business.data.util.ModelMapper
import pl.lejdi.planner.business.utils.date.format.DateFormatter
import pl.lejdi.planner.business.utils.date.setNoon

class TaskEntityMapper(
    private val dateFormatter: DateFormatter
) : ModelMapper<Task, TaskEntity>() {
    override fun mapToBusinessModel(frameworkModel: TaskEntity): Task {
        return Task(
            id = frameworkModel.id,
            name = frameworkModel.name,
            description = frameworkModel.description,
            startDate = dateFormatter.dateFromCacheFormat(frameworkModel.startDate)!!.setNoon(), // surely not null - startDate cannot be null in framework model
            endDate = dateFormatter.dateFromCacheFormat(frameworkModel.endDate)?.setNoon(),
            daysInterval = frameworkModel.daysInterval,
            hour = dateFormatter.timeFromStringFormat(frameworkModel.hour),
            asap = frameworkModel.asap
        )
    }

    override fun mapFromBusinessModel(businessModel: Task): TaskEntity {
        return TaskEntity(
            id = businessModel.id,
            name = businessModel.name,
            description = businessModel.description,
            startDate = dateFormatter.formatDateForCache(businessModel.startDate)!!, // surely not null - startDate cannot be null in business model
            endDate = dateFormatter.formatDateForCache(businessModel.endDate),
            daysInterval = businessModel.daysInterval,
            hour = dateFormatter.formatTimeToString(businessModel.hour),
            asap = businessModel.asap
        )
    }
}