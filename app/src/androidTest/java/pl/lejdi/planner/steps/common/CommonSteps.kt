package pl.lejdi.planner.steps.common

import androidx.test.core.app.ActivityScenario.launch
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import pl.lejdi.planner.business.data.cache.tasksdatesource.MockTasksDataSource
import pl.lejdi.planner.business.util.date.MockDateUtil
import pl.lejdi.planner.framework.presentation.MainActivity
import pl.lejdi.planner.steps.BaseSteps

@HiltAndroidTest
class CommonSteps : BaseSteps() {

    @And("current date is {string}")
    fun currentDateIs(date: String) {
        MockDateUtil.setMockCurrentDate(dateFormatter.parse(date)!!)
    }

    @Given("user has no saved tasks")
    fun userHasNoSavedTasks(){
        MockTasksDataSource.setupMockList(emptyList())
    }

    @When("user launches the application")
    fun userLaunchesTheApplication() {
        launch(MainActivity::class.java)
    }
}