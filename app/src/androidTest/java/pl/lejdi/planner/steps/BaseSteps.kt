package pl.lejdi.planner.steps

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.junit.WithJunitRule
import org.junit.Rule

@WithJunitRule(useAsTestClassInDescription = true)
@HiltAndroidTest
open class BaseSteps {
    @Rule(order = 0)
    @JvmField
    val hiltRule = HiltAndroidRule(this)
}