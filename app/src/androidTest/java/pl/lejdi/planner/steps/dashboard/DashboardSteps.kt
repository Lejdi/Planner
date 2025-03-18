package pl.lejdi.planner.steps.dashboard

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import io.cucumber.java.en.Then
import pl.lejdi.planner.steps.BaseSteps
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.java.en.And
import pl.lejdi.planner.framework.presentation.dashboard.ui.DashboardScreenTestTags.DASHBOARD_FAB_TEST_TAG

@HiltAndroidTest
class DashboardSteps: BaseSteps() {

    @Then("user sees no tasks on day {string}")
    fun userSeesNoTasksOnDay(date: String) {
        val dateString = displayableDateFormatter.formatDateToDisplayable(dateFormatter.parse(date))
        println(dateString)
    }

    @And("add task button is visible")
    fun addTaskButtonIsVisible() {
        onNodeWithTag(DASHBOARD_FAB_TEST_TAG).assertIsDisplayed()
    }
}