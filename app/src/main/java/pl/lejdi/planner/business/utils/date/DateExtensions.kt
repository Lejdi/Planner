package pl.lejdi.planner.business.utils.date

import android.text.format.DateUtils
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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

fun today() : Date {
    val calendar = Calendar.getInstance()
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0
    return calendar.time
}