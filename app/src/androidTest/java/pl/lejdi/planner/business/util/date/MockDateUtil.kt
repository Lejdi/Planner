package pl.lejdi.planner.business.util.date

import pl.lejdi.planner.business.utils.date.DateUtil
import java.util.Date

object MockDateUtil : DateUtil {

    private lateinit var currentDate: Date

    fun setMockCurrentDate(date: Date){
        currentDate = date
    }

    override fun getToday() = currentDate
}