package pl.lejdi.planner.business.utils.date

import javax.inject.Inject

class DefaultDateFormatProvider @Inject constructor() : DateFormatProvider {
    override val CACHE_DATE_FORMAT: String
        get() = "ddMMyyyyHHmm"
    override val DISPLAYABLE_DATE_FORMAT: String
        get() = "dd MMMM"
}