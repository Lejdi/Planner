package pl.lejdi.planner.business.usecases.taskslist

class TasksListUseCases(
    val deleteOutdatedTasks: DeleteOutdatedTasks,
    val getTasksForDashboard: GetTasksForDashboard,
    val markTaskComplete: MarkTaskComplete,
)