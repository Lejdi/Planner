package pl.lejdi.planner.business.utils

import kotlin.math.absoluteValue
import kotlin.math.sign

fun Int.ceilDiv(other: Int): Int {
    return this.floorDiv(other) + this.rem(other).sign.absoluteValue
}