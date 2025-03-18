package pl.lejdi.planner.steps.dashboard

import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import pl.lejdi.planner.framework.presentation.MainActivity
import pl.lejdi.planner.steps.BaseSteps
import androidx.test.core.app.ActivityScenario.launch
import dagger.hilt.android.testing.HiltAndroidTest

@HiltAndroidTest
class DashboardSteps: BaseSteps() {
    @Given("user has no saved tasks")
    fun userHasNoSavedTasks(){

    }

    @When("user launches the application")
    fun userLaunchesTheApplication() {
        launch(MainActivity::class.java)
    }

    @Then("user sees no tasks on dashboard")
    fun userSeesNoTasksOnDashboard() {

    }
}