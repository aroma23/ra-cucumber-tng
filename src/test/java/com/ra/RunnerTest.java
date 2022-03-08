package com.ra;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(features = {"src/test/resources"}, plugin = {"json:target/cucumber.json",
        "html:target/cucumber-html-reports/consolidated.html", "rerun:target/cucumber-html-reports/rerun.txt"})
public class RunnerTest extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

