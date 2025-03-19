package pl.lejdi.planner.business.usecases.dashboard

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
) : UseCase<Flow<UseCaseResult<List<SingleDayDataDTO>>>, Unit>() {

    override suspend fun execute(params: Unit): Flow<UseCaseResult<List<SingleDayDataDTO>>> {
        return tasksDataSource.getAllTasks().map { result ->
            if (result is CacheResult.Error) {
                UseCaseResult.Error(ErrorType.CacheError)
            }
            else {
                val cacheTasksList = (result as CacheResult.Success).data
                val tasksList = taskEntityMapper.mapListToBusinessModel(cacheTasksList)

                val resultList = mutableListOf<SingleDayDataDTO>()
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
                    resultList.add(
                        SingleDayDataDTO(dateString, sortedTasks)
                    )
                }

                UseCaseResult.Success(resultList)
            }
        }
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