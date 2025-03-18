package pl.lejdi.planner.framework.presentation.edittask.util

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.business.data.model.Time
import pl.lejdi.planner.business.utils.date.format.DateFormatter
import java.util.Date

object EditTaskFormHelper {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface DateFormatterEntryPoint{
        fun dateFormatter() : DateFormatter
    }

    private fun getDateFormatter(context: Context) : DateFormatter {
        val entryPoint = EntryPointAccessors.fromApplication(context, DateFormatterEntryPoint::class.java)
        return entryPoint.dateFormatter()
    }


    fun getSelectedRadioForTask(task: Task?) : EditTaskRadioButton {
        if(task == null || task.asap) return EditTaskRadioButton.ASAP
        return when(task.daysInterval){
            0 -> EditTaskRadioButton.SPECIFIC_DAY
            else -> EditTaskRadioButton.PERIODIC
        }
    }

    fun formatDateForDisplay(context: Context, date: Date?) : String{
        val dateFormatter = getDateFormatter(context)
        return dateFormatter.formatDateForControl(date) ?: ""
    }

    fun formatHoursForDisplay(context: Context, time: Time?) : String {
        val dateFormatter = getDateFormatter(context)
        return dateFormatter.formatTimeToString(time) ?: ""
    }
}