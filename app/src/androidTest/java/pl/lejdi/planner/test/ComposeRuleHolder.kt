package pl.lejdi.planner.test

import androidx.compose.ui.test.junit4.createEmptyComposeRule
import io.cucumber.junit.WithJunitRule
import org.junit.Rule

@WithJunitRule
object ComposeRuleHolder {
    @get:Rule
    val composeRule = createEmptyComposeRule()
}