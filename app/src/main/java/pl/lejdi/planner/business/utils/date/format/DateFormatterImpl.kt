package pl.lejdi.planner.business.utils.date.format

import pl.lejdi.planner.business.data.model.Time
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateFormatterImpl(
    dateFormatProvider: DateFormatProvider
) : DateFormatter {

    private val cacheDateFormatter: SimpleDateFormat =
        SimpleDateFormat(dateFormatProvider.CACHE_DATE_FORMAT, Locale.ENGLISH)
    private val displayableDateFormatter: SimpleDateFormat =
        SimpleDateFormat(dateFormatProvider.DISPLAYABLE_DATE_FORMAT, Locale.ENGLISH)
    private val controlDateFormatter: SimpleDateFormat =
        SimpleDateFormat(dateFormatProvider.CONTROL_DATE_FORMAT, Locale.ENGLISH)

    override fun formatDateToDisplayable(input: Date?): String? {
        return input?.let { displayableDateFormatter.format(input) }
    }

    override fun formatDateForCache(input: Date?): String? {
        return input?.let { cacheDateFormatter.format(input) }
    }

    override fun dateFromDisplayableFormat(input: String?): Date? {
        return input?.let { displayableDateFormatter.parse(input) }
    }

    override fun dateFromCacheFormat(input: String?): Date? {
        return input?.let { cacheDateFormatter.parse(input) }
    }

    override fun formatTimeToString(input: Time?): String? {
        return input?.let {
            val hour = input.hour.formatToString()
            val minute = input.minute.formatToString()
            "$hour:$minute"
        }
    }

    private fun Int.formatToString() : String {
        return if(this < 10) "0$this"
        else this.toString()
    }

    override fun timeFromStringFormat(input: String?): Time? {
        if(input == null) return null
        val splittedTime = input.split(":")
        return Time(
            hour = splittedTime[0].toInt(),
            minute = splittedTime[1].toInt()
        )
    }

    override fun formatDateForControl(input: Date?): String? {
        return input?.let { controlDateFormatter.format(input) }
    }
}