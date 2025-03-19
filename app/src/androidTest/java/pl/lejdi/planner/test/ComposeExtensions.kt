package pl.lejdi.planner.test

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.performTouchInput
import pl.lejdi.planner.business.utils.AppConfiguration.NUMBER_OF_VISIBLE_DAYS

fun SemanticsNodeInteraction.swipeToChildWithText(text: String, check: SemanticsNodeInteraction.() -> Unit) {
    var retryCount = NUMBER_OF_VISIBLE_DAYS - 1

    while (retryCount > 0) {
        retryCount--
        if (onChildren().filterToOne(hasAnyChild(hasText(text))).onChildren().filterToOne(hasText(text)).isDisplayed()) {
            check()
            return
        } else {
            performTouchInput {
                down(centerRight)
                moveTo(centerLeft)
                up()
            }
        }
    }
    throw AssertionError("No nodes with date $text found")
}