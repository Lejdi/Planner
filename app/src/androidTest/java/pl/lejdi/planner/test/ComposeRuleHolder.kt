package pl.lejdi.planner.test

import androidx.compose.ui.test.junit4.createEmptyComposeRule
import io.cucumber.junit.WithJunitRule
import org.junit.Rule

@WithJunitRule
class ComposeRuleHolder {
    @get:Rule
    var composeRule = createEmptyComposeRule()
}