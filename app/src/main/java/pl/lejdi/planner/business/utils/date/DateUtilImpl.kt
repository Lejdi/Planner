package pl.lejdi.planner.business.utils.date

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateUtilImpl(
    dateFormatProvider: DateFormatProvider
) : DateUtil {

    private val cacheDateFormatter: SimpleDateFormat = SimpleDateFormat(dateFormatProvider.CACHE_DATE_FORMAT, Locale.ROOT)
    private val displayableDateFormatter: SimpleDateFormat = SimpleDateFormat(dateFormatProvider.DISPLAYABLE_DATE_FORMAT, Locale.ROOT)

    override fun formatDateToDisplayable(input: Date): String = displayableDateFormatter.format(input)

    override fun formatDateForCache(input: Date): String = cacheDateFormatter.format(input)

    override fun dateFromDisplayableFormat(input: String): Date? = displayableDateFormatter.parse(input)

    override fun dateFromCacheFormat(input: String): Date? = cacheDateFormatter.parse(input)
}