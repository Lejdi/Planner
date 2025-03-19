package pl.lejdi.planner.steps

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.junit.WithJunitRule
import org.junit.Rule
import pl.lejdi.planner.business.utils.date.format.DateFormatter
import pl.lejdi.planner.test.ComposeRuleHolder
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@WithJunitRule(useAsTestClassInDescription = true)
@HiltAndroidTest
open class BaseSteps: SemanticsNodeInteractionsProvider by ComposeRuleHolder.composeRule {

    private val testDateFormat = "dd.MM.yyyy"
    val mockDateFormatter: SimpleDateFormat = SimpleDateFormat(testDateFormat, Locale.ENGLISH)

    @Inject
    lateinit var businessDateFormatter: DateFormatter

    @get:Rule
    val composeRule = ComposeRuleHolder.composeRule
}