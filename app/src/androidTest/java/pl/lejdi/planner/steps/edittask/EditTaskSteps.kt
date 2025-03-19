package pl.lejdi.planner.steps.edittask

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onSiblings
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.java.en.When
import pl.lejdi.planner.steps.BaseSteps
import pl.lejdi.planner.test.ComposeRuleHolder
import io.cucumber.java.en.Then
import io.cucumber.java.en.And
import pl.lejdi.planner.framework.presentation.common.ui.components.FormTextFieldTestTags.FORM_TEXT_FIELD_INPUT
import pl.lejdi.planner.framework.presentation.dashboard.ui.DashboardScreenTestTags.DASHBOARD_FAB_TEST_TAG
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenTestTags.EDIT_TASK_SCREEN
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenTestTags.EDIT_TASK_SCREEN_DELETE_BUTTON
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenTestTags.EDIT_TASK_SCREEN_SAVE_BUTTON
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenTestTags.EDIT_TASK_SCREEN_TASK_DESCRIPTION_FIELD
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenTestTags.EDIT_TASK_SCREEN_TASK_NAME_FIELD
import pl.lejdi.planner.framework.presentation.edittask.ui.RadioButtonsContainerTestTags.RADIO_BUTTON_CONTAINER_BUTTON
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenTestTags.EDIT_TASK_SCREEN_END_DATE_FIELD
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenTestTags.EDIT_TASK_SCREEN_HOUR_FIELD
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenTestTags.EDIT_TASK_SCREEN_START_DATE_FIELD
import pl.lejdi.planner.framework.presentation.edittask.ui.RadioButtonsContainerTestTags.RADIO_BUTTON_CONTAINER_INTERVAL_FIELD
import pl.lejdi.planner.framework.presentation.dashboard.ui.TaskCardTestTags.TASK_CARD_EDIT_BUTTON
import pl.lejdi.planner.framework.presentation.common.ui.components.FormTextFieldTestTags.FORM_TEXT_FIELD_ERROR_MESSAGE

@HiltAndroidTest
class EditTaskSteps(composeRuleHolder: ComposeRuleHolder) : BaseSteps(composeRuleHolder) {

    @When("user clicks on add task button")
    fun userClicksOnAddTaskButton() {
        onNodeWithTag(DASHBOARD_FAB_TEST_TAG).performClick()
    }

    @Then("edit task screen is visible")
    fun editTaskScreenIsVisible() {
        onNodeWithTag(EDIT_TASK_SCREEN).assertIsDisplayed()
    }

    @And("task name field has text {string}")
    fun taskNameFieldHasText(text: String) {
        onNodeWithTag(EDIT_TASK_SCREEN_TASK_NAME_FIELD).onChildren()
            .filterToOne(hasTestTag(FORM_TEXT_FIELD_INPUT)).assert(hasText(text))
    }

    @And("task description field has text {string}")
    fun taskDescriptionFieldHasText(text: String) {
        onNodeWithTag(EDIT_TASK_SCREEN_TASK_DESCRIPTION_FIELD).onChildren()
            .filterToOne(hasTestTag(FORM_TEXT_FIELD_INPUT)).assert(hasText(text))
    }

    @And("selected task type is {string}")
    fun selectedTaskTypeIs(taskType: String) {
        onNodeWithText(taskType, useUnmergedTree = true).onSiblings().filterToOne(hasTestTag(RADIO_BUTTON_CONTAINER_BUTTON)).assertIsSelected()
    }

    @And("delete button is visible")
    fun deleteButtonIsVisible() {
        onNodeWithTag(EDIT_TASK_SCREEN_DELETE_BUTTON).assertIsDisplayed()
    }

    @And("save button is visible")
    fun saveButtonIsVisible() {
        onNodeWithTag(EDIT_TASK_SCREEN_SAVE_BUTTON).assertIsDisplayed()
    }

    @When("user enters task name {string}")
    fun userEntersTaskName(text: String) {
        onNodeWithTag(EDIT_TASK_SCREEN_TASK_NAME_FIELD).onChildren()
            .filterToOne(hasTestTag(FORM_TEXT_FIELD_INPUT)).performTextInput(text)
    }

    @And("user enters task description {string}")
    fun userEntersTaskDescription(text: String) {
        onNodeWithTag(EDIT_TASK_SCREEN_TASK_DESCRIPTION_FIELD).onChildren()
            .filterToOne(hasTestTag(FORM_TEXT_FIELD_INPUT)).performTextInput(text)
    }

    @And("user clicks save button")
    fun userClicksSaveButton() {
        onNodeWithTag(EDIT_TASK_SCREEN_SAVE_BUTTON).performClick()
    }

    @And("start date selection field is {string}")
    fun startDateSelectionFieldIs(visible: String) {
        if(visible == "visible"){
            onNodeWithTag(EDIT_TASK_SCREEN_START_DATE_FIELD).assertIsDisplayed()
        }
        else{
            onNodeWithTag(EDIT_TASK_SCREEN_START_DATE_FIELD).assertDoesNotExist()
        }
    }

    @And("hour selection field is {string}")
    fun hourSelectionFieldIs(visible: String) {
        if(visible == "visible"){
            onNodeWithTag(EDIT_TASK_SCREEN_HOUR_FIELD).assertIsDisplayed()
        }
        else{
            onNodeWithTag(EDIT_TASK_SCREEN_HOUR_FIELD).assertDoesNotExist()
        }
    }

    @And("end date selection field is {string}")
    fun endDateSelectionFieldIs(visible: String) {
        if(visible == "visible"){
            onNodeWithTag(EDIT_TASK_SCREEN_END_DATE_FIELD).assertIsDisplayed()
        }
        else{
            onNodeWithTag(EDIT_TASK_SCREEN_END_DATE_FIELD).assertDoesNotExist()
        }
    }



    @And("days interval field is {string}")
    fun daysIntervalFieldIs(visible: String) {
        if(visible == "visible"){
            onNodeWithTag(RADIO_BUTTON_CONTAINER_INTERVAL_FIELD).assertIsDisplayed()
        }
        else{
            onNodeWithTag(RADIO_BUTTON_CONTAINER_INTERVAL_FIELD).assertDoesNotExist()
        }
    }

    @When("user changes task type to {string}")
    fun userChangesTaskTypeTo(taskType: String) {
        onNodeWithText(taskType, useUnmergedTree = true).onSiblings().filterToOne(hasTestTag(RADIO_BUTTON_CONTAINER_BUTTON)).performClick()
    }

    @And("user enters days interval of {string}")
    fun userEntersDaysIntervalOf(interval: String) {
        onNodeWithTag(RADIO_BUTTON_CONTAINER_INTERVAL_FIELD).performTextInput(interval)
    }

    @When("user click on edit button of task with name {string} on day {string}")
    fun userClickOnEditButtonOfTaskWithNameOnDay(taskName: String, date: String) {
        val dateString = businessDateFormatter.formatDateToDisplayable(mockDateFormatter.parse(date))
        onAllNodesWithText(taskName).filterToOne(hasParent(hasAnySibling(hasText(dateString!!))))
            .onChildren().filterToOne(hasTestTag(TASK_CARD_EDIT_BUTTON)).performClick()
    }

    @And("task name validation text is {string}")
    fun taskNameValidationTextIs(visible: String) {
        onNodeWithTag(EDIT_TASK_SCREEN_TASK_NAME_FIELD).onChildren().filterToOne(hasTestTag(FORM_TEXT_FIELD_ERROR_MESSAGE)).apply {
            if(visible == "visible"){
                assertIsDisplayed()
            }
            else{
                assertDoesNotExist()
            }
        }
    }

    @When("user clicks delete button")
    fun userClicksDeleteButton() {
        onNodeWithTag(EDIT_TASK_SCREEN_DELETE_BUTTON).performClick()
    }
}