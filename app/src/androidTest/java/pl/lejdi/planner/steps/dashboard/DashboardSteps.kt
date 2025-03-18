package pl.lejdi.planner.steps.dashboard

import io.cucumber.java.en.Then
import pl.lejdi.planner.steps.BaseSteps
import dagger.hilt.android.testing.HiltAndroidTest

@HiltAndroidTest
class DashboardSteps: BaseSteps() {

    @Then("user sees no tasks on day {string}")
    fun userSeesNoTasksOnDay(date: String) {
        val dateString = displayableDateFormatter.formatDateToDisplayable(dateFormatter.parse(date))
        println(dateString)
    }
}