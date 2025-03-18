package pl.lejdi.planner.steps.common

import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.java.en.And
import pl.lejdi.planner.business.util.date.MockDateUtil
import pl.lejdi.planner.steps.BaseSteps

@HiltAndroidTest
class CommonSteps : BaseSteps() {

    @And("current date is {string}")
    fun currentDateIs(date: String) {
        MockDateUtil.setMockCurrentDate(dateFormatter.parse(date)!!)
    }
}