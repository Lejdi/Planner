package pl.lejdi.planner.test

import io.cucumber.junit.CucumberOptions

@CucumberOptions(
    glue = [ "pl.lejdi.planner.steps" ],
    features = [ "features" ]
)
class CucumberConfiguration {
}