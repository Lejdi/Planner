package pl.lejdi.planner.business.usecases.dashboard

class DashboardUseCases(
    val deleteOutdatedTasks: DeleteOutdatedTasks,
    val getTasksForDashboard: GetTasksForDashboard,
    val markTaskComplete: MarkTaskComplete,
)