package pl.lejdi.planner.steps

import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.junit.WithJunitRule
import pl.lejdi.planner.business.utils.date.format.DateFormatter
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@WithJunitRule(useAsTestClassInDescription = true)
@HiltAndroidTest
open class BaseSteps {

    private val testDateFormat = "dd.MM.yyyy"
    val dateFormatter: SimpleDateFormat = SimpleDateFormat(testDateFormat, Locale.ENGLISH)

    @Inject
    lateinit var displayableDateFormatter: DateFormatter
}