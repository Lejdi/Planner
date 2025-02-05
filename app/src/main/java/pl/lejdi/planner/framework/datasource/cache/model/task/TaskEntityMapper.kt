package pl.lejdi.planner.framework.datasource.cache.model.task

import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.business.data.util.ModelMapper

class TaskEntityMapper : ModelMapper<Task, TaskEntity>() {
    override fun mapToBusinessModel(frameworkModel: TaskEntity): Task {
        return Task(
            frameworkModel.id,
            frameworkModel.name
        )
    }

    override fun mapFromBusinessModel(businessModel: Task): TaskEntity {
        return TaskEntity(
            id = businessModel.id,
            name = businessModel.title,
            description = "",
            startDate = "",
            endDate = "",
            daysInterval = 0,
            hour = "",
            asap = true
        )
    }
}