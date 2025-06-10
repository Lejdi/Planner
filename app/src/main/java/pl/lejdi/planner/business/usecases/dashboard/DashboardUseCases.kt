package pl.lejdi.planner.business.usecases.dashboard

class DashboardUseCases(
    val updateTasksDates: UpdateTasksDates,
    val getTasksForDashboard: GetTasksForDashboard,
    val markTaskComplete: MarkTaskComplete,
)