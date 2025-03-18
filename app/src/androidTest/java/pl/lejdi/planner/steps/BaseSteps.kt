package pl.lejdi.planner.steps

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.junit.WithJunitRule
import org.junit.Rule
import java.text.SimpleDateFormat
import java.util.Locale

@WithJunitRule(useAsTestClassInDescription = true)
@HiltAndroidTest
open class BaseSteps {

    private val testDateFormat = "dd.MM.yyyy"
    val dateFormatter: SimpleDateFormat = SimpleDateFormat(testDateFormat, Locale.ENGLISH)

    @Rule(order = 0)
    @JvmField
    val hiltRule = HiltAndroidRule(this)
}