package pl.lejdi.planner.business.usecases.dashboard

import pl.lejdi.planner.business.data.cache.tasksdatasource.TasksDataSource
import pl.lejdi.planner.business.data.cache.util.CacheResult
import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.business.usecases.UseCase
import pl.lejdi.planner.business.usecases.UseCaseResult
import pl.lejdi.planner.business.utils.AppConfiguration.NUMBER_OF_VISIBLE_DAYS
import pl.lejdi.planner.business.utils.date.DateUtil
import pl.lejdi.planner.business.utils.date.format.DateFormatter
import pl.lejdi.planner.business.utils.date.addDays
import pl.lejdi.planner.business.utils.date.daysSinceDate
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntityMapper
import pl.lejdi.planner.framework.presentation.common.model.task.SingleDayDataDTO
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayable
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayableMapper
import pl.lejdi.planner.framework.presentation.util.ErrorType
import java.util.Date

class GetTasksForDashboard(
    private val tasksDataSource: TasksDataSource,
    private val taskEntityMapper: TaskEntityMapper,
    private val taskDisplayableMapper: TaskDisplayableMapper,
    private val dateFormatter: DateFormatter,
    private val dateUtil: DateUtil
) : UseCase<UseCaseResult<List<SingleDayDataDTO>>, Unit>() {

    override suspend fun execute(params: Unit): UseCaseResult<List<SingleDayDataDTO>> {
        val cacheResult = tasksDataSource.getAllTasks()
        if (cacheResult is CacheResult.Error) {
            return UseCaseResult.Error(ErrorType.CacheError)
        }
        val cacheTasksList = (cacheResult as CacheResult.Success).data
        val tasksList = taskEntityMapper.mapListToBusinessModel(cacheTasksList)

        val result = mutableListOf<SingleDayDataDTO>()
        val today = dateUtil.getToday()
        for (i in 0 until NUMBER_OF_VISIBLE_DAYS) {
            val filledDate = today.addDays(i)
            val dateString = dateFormatter.formatDateToDisplayable(filledDate)!!
            val tasksForDate = taskDisplayableMapper.mapListFromBusinessModel(
                filterTasksForDate(
                    filledDate,
                    tasksList
                )
            )
            val sortedTasks = tasksForDate
                .sortedWith(compareBy<TaskDisplayable> { it.priority }.thenBy { it.hour } )
            result.add(
                SingleDayDataDTO(dateString, sortedTasks)
            )
        }

        return UseCaseResult.Success(result)
    }

    private fun filterTasksForDate(date: Date, tasksList: List<Task>): List<Task> {
        val filteredTasks = mutableListOf<Task>()
        for (task in tasksList) {
            val daysFromTheStart = date.daysSinceDate(task.startDate)
            val daysSinceTheEnd = task.endDate.daysSinceDate(date)
            //asap tasks must be done "today"
            if (task.asap && dateUtil.isToday(date)) {
                filteredTasks.add(task)
                continue
            }
            //one time tasks for specified date
            if (task.daysInterval == 0 && daysFromTheStart == 0) {
                filteredTasks.add(task)
                continue
            }
            //periodic tasks that happens in this date
            if (
                task.daysInterval > 0
                && task.startDate <= date
                && daysFromTheStart % task.daysInterval == 0
                && daysSinceTheEnd <= 0
            ) {
                filteredTasks.add(task)
                continue
            }
        }
        return filteredTasks
    }
}