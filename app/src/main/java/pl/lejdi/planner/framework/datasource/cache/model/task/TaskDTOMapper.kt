package pl.lejdi.planner.framework.datasource.cache.model.task

import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.business.data.util.ModelMapper

class TaskDTOMapper : ModelMapper<Task, TaskDTO>() {
    override fun mapToBusinessModel(frameworkModel: TaskDTO): Task {
        return Task("1" ,"2")
    }

    override fun mapFromBusinessModel(businessModel: Task): TaskDTO {
        return TaskDTO()
    }
}