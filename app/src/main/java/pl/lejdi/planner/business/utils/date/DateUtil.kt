package pl.lejdi.planner.business.utils.date

import java.util.Date

interface DateUtil {
    fun formatDateToDisplayable(input: Date) : String

    fun formatDateForCache(input: Date) : String

    fun dateFromDisplayableFormat(input: String) : Date?

    fun dateFromCacheFormat(input: String) : Date?
}