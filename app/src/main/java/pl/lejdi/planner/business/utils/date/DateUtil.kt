package pl.lejdi.planner.business.utils.date

import java.util.Calendar
import java.util.Date

abstract class DateUtil {
    abstract fun getToday() : Date
    fun isToday(date: Date) : Boolean{
        val today = getToday()
        val todayCalendar = Calendar.getInstance()
        todayCalendar.time = today

        val verifiedDateCalendar = Calendar.getInstance()
        verifiedDateCalendar.time = date

        val yearsTheSame = todayCalendar.get(Calendar.YEAR) == verifiedDateCalendar.get(Calendar.YEAR)
        val daysTheSame = todayCalendar.get(Calendar.DAY_OF_YEAR) == verifiedDateCalendar.get(Calendar.DAY_OF_YEAR)

        return yearsTheSame and daysTheSame
    }
}