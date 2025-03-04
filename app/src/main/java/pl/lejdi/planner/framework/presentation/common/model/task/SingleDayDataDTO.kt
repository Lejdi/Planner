package pl.lejdi.planner.framework.presentation.common.model.task

data class SingleDayDataDTO(
    val date: String,
    val tasks: List<TaskDisplayable>
)
