package pl.lejdi.planner.framework.presentation.common.model.task

import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.business.data.util.ModelMapper
import pl.lejdi.planner.business.utils.date.DateFormatter

class TaskDisplayableMapper(
    private val dateFormatter: DateFormatter
) : ModelMapper<Task, TaskDisplayable>() {
    override fun mapToBusinessModel(frameworkModel: TaskDisplayable): Task {
        return Task(
            id = frameworkModel.id,
            name = frameworkModel.name,
            description = frameworkModel.description,
            startDate = dateFormatter.dateFromDisplayableFormat(frameworkModel.startDate)!!, // surely not null - startDate cannot be null in framework model
            endDate = dateFormatter.dateFromDisplayableFormat(frameworkModel.endDate),
            daysInterval = frameworkModel.daysInterval.toInt(),
            hour = frameworkModel.hour,
            asap = frameworkModel.asap
        )
    }

    override fun mapFromBusinessModel(businessModel: Task): TaskDisplayable {
        return TaskDisplayable(
            id = businessModel.id,
            name = businessModel.name,
            description = businessModel.description,
            startDate = dateFormatter.formatDateToDisplayable(businessModel.startDate)!!, // surely not null - startDate cannot be null in business model
            endDate = dateFormatter.formatDateToDisplayable(businessModel.endDate),
            daysInterval = businessModel.daysInterval.toString(),
            hour = businessModel.hour,
            asap = businessModel.asap
        )
    }
}