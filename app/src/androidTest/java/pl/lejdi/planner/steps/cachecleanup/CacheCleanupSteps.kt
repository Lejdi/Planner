package pl.lejdi.planner.steps.cachecleanup

import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import kotlinx.coroutines.runBlocking
import pl.lejdi.planner.business.data.cache.datastore.LastCacheCleanupDataStoreInteractor
import pl.lejdi.planner.business.data.cache.tasksdatesource.MockTasksDataSource
import pl.lejdi.planner.business.data.cache.util.CacheResult
import pl.lejdi.planner.steps.BaseSteps
import pl.lejdi.planner.test.ComposeRuleHolder
import javax.inject.Inject

@HiltAndroidTest
class CacheCleanupSteps(composeRuleHolder: ComposeRuleHolder) : BaseSteps(composeRuleHolder) {

    @Inject
    lateinit var lastCacheCleanupDataStoreInteractor: LastCacheCleanupDataStoreInteractor

    @Given("the last cleanup date is {string}")
    fun theLastCleanupDateIs(date: String) {
        val dateToSave = mockDateFormatter.parse(date)
        runBlocking {
            lastCacheCleanupDataStoreInteractor.setData(dateToSave!!)
        }
    }

    @Then("tasks saved in cache are")
    fun tasksSavedInCacheAre(tasksIds: List<String>) {
        runBlocking {
            val result = MockTasksDataSource.getAllTasks()
            val savedTasks = (result as CacheResult.Success).data
            assert(savedTasks.all { savedTask -> tasksIds.contains(savedTask.id.toString()) })
        }
    }

}