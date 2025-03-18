package pl.lejdi.planner.business.utils.date.format

import pl.lejdi.planner.business.data.model.Time
import java.util.Date

interface DateFormatter {
    fun formatDateToDisplayable(input: Date?) : String?

    fun formatDateForCache(input: Date?) : String?

    fun dateFromDisplayableFormat(input: String?) : Date?

    fun dateFromCacheFormat(input: String?) : Date?

    fun formatTimeToString(input: Time?) : String?

    fun timeFromStringFormat(input: String?) : Time?

    fun formatDateForControl(input: Date?) : String?
}