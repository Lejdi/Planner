package pl.lejdi.planner.steps.common

import androidx.test.core.app.ActivityScenario.launch
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import pl.lejdi.planner.business.data.cache.tasksdatesource.MockTasksDataSource
import pl.lejdi.planner.business.util.date.MockDateUtil
import pl.lejdi.planner.framework.presentation.MainActivity
import pl.lejdi.planner.steps.BaseSteps
import pl.lejdi.planner.framework.datasource.cache.model.task.TaskEntity
import pl.lejdi.planner.test.ComposeRuleHolder

@HiltAndroidTest
class CommonSteps(composeRuleHolder: ComposeRuleHolder) : BaseSteps(composeRuleHolder) {

    @And("current date is {string}")
    fun currentDateIs(date: String) {
        MockDateUtil.setMockCurrentDate(mockDateFormatter.parse(date)!!)
    }

    @Given("user has no saved tasks")
    fun userHasNoSavedTasks(){
        MockTasksDataSource.clearList()
    }

    @When("user launches the application")
    fun userLaunchesTheApplication() {
        launch(MainActivity::class.java)
    }

    @And("the user has saved tasks")
    fun theUserHasSavedTasks(tasksList: DataTable) {
        MockTasksDataSource.clearList()
        MockTasksDataSource.setupMockList(
            tasksList.asMaps().map {
                TaskEntity(
                    id = it["taskID"]?.toInt()!!,
                    name = it["taskName"]!!,
                    description = it["description"],
                    startDate = mockDateFormatToEntityDateFormat(it["startDate"])!!,
                    endDate = mockDateFormatToEntityDateFormat(it["endDate"]),
                    hour = it["hour"],
                    daysInterval = it["daysInterval"]?.toInt() ?: 0,
                    asap = it["asap"] == "yes"
                )
            }
        )
    }

    private fun mockDateFormatToEntityDateFormat(mockDate: String?) : String?{
        return mockDate?.let{
            val date = mockDateFormatter.parse(it)
            businessDateFormatter.formatDateForCache(date)
        }
    }
}