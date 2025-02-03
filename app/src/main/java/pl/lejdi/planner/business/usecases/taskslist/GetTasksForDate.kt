package pl.lejdi.planner.business.usecases.taskslist

import pl.lejdi.planner.business.usecases.UseCase
import pl.lejdi.planner.business.usecases.UseCaseResult
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayable
import java.util.Date

class GetTasksForDate : UseCase<UseCaseResult<List<TaskDisplayable>>, Date>() {
    override suspend fun execute(params: Date): UseCaseResult<List<TaskDisplayable>> {
        return UseCaseResult.Success(emptyList())
    }
}