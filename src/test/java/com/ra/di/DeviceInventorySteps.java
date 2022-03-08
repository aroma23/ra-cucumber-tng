package com.ra.di;

import com.ra.enums.Component;
import com.ra.fw.RAAbstractTest;
import com.ra.fw.Util;
import com.ra.CommonSteps;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.*;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class DeviceInventorySteps extends RAAbstractTest {
    private Response response;
    private final CommonSteps commonSteps;

    public DeviceInventorySteps(CommonSteps commonSteps) {
        super(Arrays.asList(Component.DEVICE_PORTAL));
        this.commonSteps = commonSteps;
//        this.commonSteps.setJrs(() -> {
//            commonSteps.setJsonResponseString(jsonResponseString);
//            return "true";
//        });
        scenario = commonSteps.getScenario();
    }

    @Then("Get Decommissioned Devices API should respond with expectedStatusCode: {int}")
    public void getDeviceInventoryAPIShouldRespondWithExpectedStatusCodeExpectedStatusCode(int expectedStatusCode) {
        logger.debug("Get Decommission devices response code: " + response.getStatusCode() + " "
                + response.getStatusLine());
        response.then()
//                .log().all()
                .statusCode(200);
        logger.trace("Get Decommission devices response: " + response.body().asString());
    }

    @When("Get Decommissioned Devices API called with page: {string} and size: {string}")
    public void getDeviceInventoryAPICalledWithPagePageNoAndSizePageSize(String pageNo, String pageSize) {
        Map<String, String> query = Util.formQueryMap(String.format("?page=%s&pageSize=%s", pageNo, pageSize));
        logger.debug("queryParam: " + query);
        response = dp.client.getDecommissionDevices(query);
    }

    @Then("Get Decommissioned Devices API should respond with total pages more than {int}")
    public void getDecommissionedDevicesAPIShouldRespondWithTotalPagesMoreThan(int totalPages) {
        response.then().assertThat().body("pagination.totalPages", greaterThanOrEqualTo(totalPages));
    }

    @Then("Get Decommissioned Devices API should respond device id {int}")
    public void getDecommissionedDevicesAPIShouldRespondDeviceId(int deviceId) {
        int temp = 76503;
        response.then().assertThat().body("data.id", hasItems(deviceId, temp));
    }

}