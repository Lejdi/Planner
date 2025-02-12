package pl.lejdi.planner.framework.presentation.common.model.task

data class TaskDisplayable (
    val id: Int,
    val name: String,
    val description: String?,
    val startDate: String,
    val endDate: String?,
    val hour: String?,
    val daysInterval: String,
    val asap: Boolean,
    val priority: Int, //order of displaying on list, lower number equals bigger priority
)