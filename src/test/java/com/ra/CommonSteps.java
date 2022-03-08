package com.ra;

import com.ra.fw.Util;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Data
public class CommonSteps {
    private static final Logger logger = LogManager.getLogger(CommonSteps.class);
    private String jsonResponseString;
    private int responseStatusCode;
//    private Supplier<Response> health;
    private Supplier<String> jrs; //set jsonResponseString from called class
    private Supplier<Integer> jsc; //set response status code from called class
    private Scenario scenario;

    @Before
    public void setUp(Scenario scenario) {
        logger.info("========== Scenario: "
                + scenario.getName() + ": Line: " + scenario.getLine() + " started ==========");
        this.scenario = scenario;
    }

    @When("Get health check API called")
    public void getHealthCheckAPICalled() {
//        response = health.get();
    }

    @Then("Get health check API should respond with expectedStatusCode: {int}")
    public void getHealthCheckAPIShouldRespondWithExpectedStatusCode(int expectedStatusCode) {
//        logger.debug("Get health check response code: {}", response.status());
//        Assert.assertEquals("Get health check status doesn't match with expected", expectedStatusCode,
//                response.status());
    }

    @And("Get health check API respond have {string} status")
    public void getHealthCheckAPIRespondHaveUPStatus(String state) throws JSONException {
//        jsonResponseString = response.body().toString();
        logger.trace("Get health check response: {}", jsonResponseString);
        Assert.assertEquals("Get health check response doesn't match with expected",
                new JSONObject(jsonResponseString).getString("status"), state);
    }

    @And("Get {string} api response should match with {string}: {string}")
    public void getDeviceInventoryAPIResponseShouldMatchWithSchema(String api, String type, String values) {
        jrs.get();
        Map<String, Boolean> mismatches = Util.validateJson(Arrays.asList(values.split("#")), jsonResponseString);
        Assert.assertEquals(type + " Validation failed for '" + api + "' : mismatches on these field(s) : " + mismatches.toString(),
                0, mismatches.size());
    }

    @Then("wait for {int} seconds")
    public void waitForSeconds(int secs) {
//        Thread.sleep(secs * 1000L);
        Util.Sync.waitUntil(secs, secs, secs, () -> true);
    }

    @And("placeholder to retain objective: {string}")
    public void placeholderToRetainDescDesc(String objective) {

    }

    @Then("Get {string} api should response with statusCode: {int}")
    public void getApiShouldResponseWithStatusCodeExpectedStatusCode(String api, int expectedStatusCode) {
        jsc.get();
        Assert.assertEquals("Get " + api + " status doesn't match with expected", expectedStatusCode,
                responseStatusCode);
        logger.debug("Get " + api + " status code: " + responseStatusCode);
        jrs.get();
        logger.trace("Get " + api + " response: " + jsonResponseString);
    }

    @And("Get {string} api response should contain these fields: {string}")
    public void getAPIResponseShouldContainTheseFields(String api, String field) {
        jsc.get();
        jrs.get();
        logger.trace("Get " + api + " response: " + jsonResponseString);
        logger.debug("Get " + api + " status code: " + responseStatusCode);
        List<String> fields = Arrays.asList(field.split("#"));
        Map<String, Boolean> mismatches = Util.validateJson(fields, jsonResponseString);
        Assert.assertEquals("Schema Validation for '" + api + "' failed: mismatches on these field(s) : " + mismatches,
                0, mismatches.size());
        logger.debug("Schema validation of service is completed");
    }
}
