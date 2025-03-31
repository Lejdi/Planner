package pl.lejdi.planner.business.utils.date

import java.time.Duration
import java.util.Calendar
import java.util.Date

fun Date?.daysSinceDate(date: Date?): Int {
    if(this == null || date == null){
        return -1
    }
    val thisCalendar = Calendar.getInstance()
    thisCalendar.time = this
    thisCalendar.add(Calendar.MILLISECOND, thisCalendar[Calendar.DST_OFFSET] )

    val dateCalendar = Calendar.getInstance()
    dateCalendar.time = date
    dateCalendar.add(Calendar.MILLISECOND, dateCalendar[Calendar.DST_OFFSET])
    return Duration.between(thisCalendar.toInstant(), dateCalendar.toInstant()).toDays().toInt()
}

fun Date.addDays(days: Int) : Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DATE, days)
    return calendar.time
}

fun Date.setNoon() : Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar[Calendar.HOUR_OF_DAY] = 12
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0
    return calendar.time
}