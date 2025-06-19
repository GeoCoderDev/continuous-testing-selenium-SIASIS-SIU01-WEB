package com.siasis.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.siasis.stepDefinitions", "com.siasis.hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/index.html",
                "json:target/cucumber/cucumber.json"
        },
        monochrome = true
)
public class RunnerTest {
}