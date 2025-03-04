package pl.lejdi.planner.business.utils.date

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
}