package pl.lejdi.planner.framework.presentation.util

import pl.lejdi.planner.R

enum class ErrorType(val details: Int) {
    CacheError(R.string.error_type_cache),
    Unknown(R.string.error_type_unknown)
}