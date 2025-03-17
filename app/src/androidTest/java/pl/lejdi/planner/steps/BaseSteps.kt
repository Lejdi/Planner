package pl.lejdi.planner.steps

import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import pl.lejdi.planner.framework.presentation.MainActivity

open class BaseSteps {
    @JvmField
    @Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)
}