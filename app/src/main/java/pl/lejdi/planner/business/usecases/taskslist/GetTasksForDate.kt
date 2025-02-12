package pl.lejdi.planner.business.usecases.taskslist

import pl.lejdi.planner.business.data.cache.tasksdatasource.TasksDataSource
import pl.lejdi.planner.business.data.cache.utl.CacheResult
import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.business.usecases.UseCase
import pl.lejdi.planner.business.usecases.UseCaseResult
import pl.lejdi.planner.business.utils.date.addDays
import pl.lejdi.planner.business.utils.date.daysSinceDate
import pl.lejdi.planner.business.utils.date.isToday
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntityMapper
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayable
import pl.lejdi.planner.framework.presentation.common.model.task.TaskDisplayableMapper
import pl.lejdi.planner.framework.presentation.util.ErrorType
import java.util.Date

class GetTasksForDashboard(
    private val tasksDataSource: TasksDataSource,
    private val taskEntityMapper: TaskEntityMapper,
    private val taskDisplayableMapper: TaskDisplayableMapper,
) : UseCase<UseCaseResult<Map<Date, List<TaskDisplayable>>>, Unit>() {

    override suspend fun execute(params: Unit): UseCaseResult<Map<Date, List<TaskDisplayable>>> {
        val cacheResult = tasksDataSource.getAllTasks()
        if(cacheResult is CacheResult.Error){
            return UseCaseResult.Error(ErrorType.CacheError)
        }
        val cacheTasksList = (cacheResult as CacheResult.Success).data
        val tasksList = taskEntityMapper.mapListToBusinessModel(cacheTasksList)

        val map = mutableMapOf<Date, List<TaskDisplayable>>()
        val today = Date()
        for(i in 0..7){ //create map of tasks today and for the next week - business requirement to display only this range
            val filledDate = today.addDays(i)
            map[filledDate] = taskDisplayableMapper.mapListFromBusinessModel(filterTasksForDate(filledDate, tasksList))
        }

        return UseCaseResult.Success(map)
    }

    private fun filterTasksForDate(date: Date, tasksList: List<Task>) : List<Task> {
        val filteredTasks = mutableListOf<Task>()
        for(task in tasksList){
            val daysFromTheStart = date.daysSinceDate(task.startDate)
            val daysSinceTheEnd = task.endDate.daysSinceDate(date)
            //asap tasks must be done "today"
            if(task.asap && date.isToday()) {
                filteredTasks.add(task)
                continue
            }
            //one time tasks for specified date
            if(task.daysInterval == 0 && daysFromTheStart == 0){
                filteredTasks.add(task)
                continue
            }
            //periodic tasks that happens in this date
            if(task.daysInterval > 0 && daysFromTheStart % task.daysInterval == 0 && daysSinceTheEnd < 0){
                filteredTasks.add(task)
                continue
            }
        }
        return filteredTasks
    }
}