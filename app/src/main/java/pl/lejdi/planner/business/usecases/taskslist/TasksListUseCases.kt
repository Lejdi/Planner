package pl.lejdi.planner.business.usecases.taskslist

class TasksListUseCases(
    val deleteOutdatedTasks: DeleteOutdatedTasks,
    val getTasksForDate: GetTasksForDate,
    val markTaskComplete: MarkTaskComplete,
)