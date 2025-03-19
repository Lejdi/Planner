package pl.lejdi.planner.steps.dashboard

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import io.cucumber.java.en.Then
import pl.lejdi.planner.steps.BaseSteps
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.java.en.And
import pl.lejdi.planner.framework.presentation.dashboard.ui.DashboardScreenTestTags.DASHBOARD_FAB_TEST_TAG
import pl.lejdi.planner.framework.presentation.dashboard.ui.DashboardScreenTestTags.DASHBOARD_TASKS_PAGER_TAG
import pl.lejdi.planner.framework.presentation.dashboard.ui.SingleDayViewTestTags.SINGLE_DAY_TASKS_COLUMN_TAG
import pl.lejdi.planner.test.swipeToChildWithText

@HiltAndroidTest
class DashboardSteps: BaseSteps() {

    @Then("user sees no tasks on day {string}")
    fun userSeesNoTasksOnDay(date: String) {
        val dateString = businessDateFormatter.formatDateToDisplayable(mockDateFormatter.parse(date))

        onNodeWithTag(DASHBOARD_TASKS_PAGER_TAG).swipeToChildWithText(dateString!!) {
            onChildren().filterToOne(hasAnyChild(hasText(dateString))).apply {
                assertIsDisplayed()
                onChildren().filterToOne(hasTestTag(SINGLE_DAY_TASKS_COLUMN_TAG)).onChildren().assertCountEquals(0)
            }
        }
    }

    @And("add task button is visible")
    fun addTaskButtonIsVisible() {
        onNodeWithTag(DASHBOARD_FAB_TEST_TAG).assertIsDisplayed()
    }
}