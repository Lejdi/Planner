package pl.lejdi.planner.business.utils.date.format

interface DateFormatProvider {
    val CACHE_DATE_FORMAT: String
    val DISPLAYABLE_DATE_FORMAT: String
    val CONTROL_DATE_FORMAT: String
}