package pl.lejdi.planner.business.utils.date

import java.util.Date

class DefaultDateUtil : DateUtil {
    override fun getToday() : Date{
        val today = Date()
        today.setMidnight()
        return today
    }
}