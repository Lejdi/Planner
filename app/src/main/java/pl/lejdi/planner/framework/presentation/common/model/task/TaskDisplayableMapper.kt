package pl.lejdi.planner.framework.presentation.common.model.task

import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.business.data.util.ModelMapper

class TaskDisplayableMapper : ModelMapper<Task, TaskDisplayable>() {
    override fun mapToBusinessModel(frameworkModel: TaskDisplayable): Task {
        return Task(1, "2")
    }

    override fun mapFromBusinessModel(businessModel: Task): TaskDisplayable {
        return TaskDisplayable()
    }
}