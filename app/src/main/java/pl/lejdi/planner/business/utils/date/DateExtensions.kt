package pl.lejdi.planner.business.utils.date

import android.text.format.DateUtils
import java.time.Duration
import java.util.Calendar
import java.util.Date

fun Date?.daysSinceDate(date: Date?): Int {
    if(this == null || date == null){
        return -1
    }
    return Duration.between(this.toInstant(), date.toInstant()).toDays().toInt()
}

fun Date.isToday() = DateUtils.isToday(this.time)

fun Date.addDays(days: Int) : Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DATE, days)
    return calendar.time
}