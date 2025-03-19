package pl.lejdi.planner.steps.dashboard

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import io.cucumber.java.en.Then
import pl.lejdi.planner.steps.BaseSteps
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.And
import pl.lejdi.planner.framework.presentation.dashboard.ui.DashboardScreenTestTags.DASHBOARD_FAB_TEST_TAG
import pl.lejdi.planner.framework.presentation.dashboard.ui.DashboardScreenTestTags.DASHBOARD_TASKS_PAGER_TAG
import pl.lejdi.planner.framework.presentation.dashboard.ui.SingleDayViewTestTags.SINGLE_DAY_TASKS_COLUMN_TAG
import pl.lejdi.planner.framework.presentation.dashboard.ui.TaskCardTestTags.TASK_CARD_COMPLETE_BUTTON
import pl.lejdi.planner.framework.presentation.dashboard.ui.TaskCardTestTags.TASK_CARD_DESCRIPTION
import pl.lejdi.planner.framework.presentation.dashboard.ui.TaskCardTestTags.TASK_CARD_EDIT_BUTTON
import pl.lejdi.planner.framework.presentation.dashboard.ui.TaskCardTestTags.TASK_CARD_HOUR
import pl.lejdi.planner.framework.presentation.dashboard.ui.TaskCardTestTags.TASK_CARD_NAME
import pl.lejdi.planner.test.swipeToChildWithText
import io.cucumber.java.en.When
import pl.lejdi.planner.test.ComposeRuleHolder

@HiltAndroidTest
class DashboardSteps(composeRuleHolder: ComposeRuleHolder) : BaseSteps(composeRuleHolder) {

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

    @And("user sees tasks on day {string}")
    fun userSeesTasksOnDay(date: String, taskCards: DataTable) {
        val dateString = businessDateFormatter.formatDateToDisplayable(mockDateFormatter.parse(date))

        onNodeWithTag(DASHBOARD_TASKS_PAGER_TAG, useUnmergedTree = true).swipeToChildWithText(dateString!!) {
            onChildren().filterToOne(hasAnyChild(hasText(dateString))).apply {
                assertIsDisplayed()
                onChildren().filterToOne(hasTestTag(SINGLE_DAY_TASKS_COLUMN_TAG)).onChildren().apply {
                    taskCards.asMaps().forEachIndexed { index, entries ->
                        val taskName = entries["taskName"]!!
                        val taskDescription = entries["taskDescription"]
                        val hour = entries["hour"]
                        val buttonsVisible = entries["buttonsVisible"] == "yes"

                        get(index).onChildren().apply {
                            filterToOne(hasTestTag(TASK_CARD_NAME)).assert(hasText(taskName))
                            filterToOne(hasTestTag(TASK_CARD_DESCRIPTION)).assert(hasText(taskDescription ?: ""))
                            if(hour == null){
                                filterToOne(hasTestTag(TASK_CARD_HOUR)).assertDoesNotExist()
                            }
                            else{
                                filterToOne(hasTestTag(TASK_CARD_HOUR)).assert(hasText(hour))
                            }
                            if(buttonsVisible){
                                filterToOne(hasTestTag(TASK_CARD_COMPLETE_BUTTON)).assertIsDisplayed()
                                filterToOne(hasTestTag(TASK_CARD_EDIT_BUTTON)).assertIsDisplayed()
                            }
                            else{

                                filterToOne(hasTestTag(TASK_CARD_COMPLETE_BUTTON)).assertDoesNotExist()
                                filterToOne(hasTestTag(TASK_CARD_EDIT_BUTTON)).assertDoesNotExist()
                            }
                        }
                    }
                }
            }
        }
    }

    @When("user completes task with name {string} on day {string}")
    fun userCompletesTaskWithName(name: String, date: String) {
        val dateString = businessDateFormatter.formatDateToDisplayable(mockDateFormatter.parse(date))
        onAllNodesWithText(name).filterToOne(hasParent(hasAnySibling(hasText(dateString!!))))
            .onChildren().filterToOne(hasTestTag(TASK_CARD_COMPLETE_BUTTON)).performClick()
    }

    @When("user clicks on card with task name {string} on day {string}")
    fun userClicksOnCardWithTaskNameOnDay(name: String, date: String) {
        val dateString = businessDateFormatter.formatDateToDisplayable(mockDateFormatter.parse(date))
        onAllNodesWithText(name).filterToOne(hasParent(hasAnySibling(hasText(dateString!!)))).performClick()
    }
}