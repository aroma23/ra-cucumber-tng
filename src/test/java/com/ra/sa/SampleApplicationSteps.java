package com.ra.sa;

import com.ra.CommonSteps;
import com.ra.enums.Component;
import com.ra.fw.RAAbstractTest;
import com.ra.fw.Util;
import com.ra.models.responses.sa.User;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;

import java.io.File;
import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class SampleApplicationSteps extends RAAbstractTest {
    private final CommonSteps commonSteps;
    private Response response;

    public SampleApplicationSteps(CommonSteps commonSteps) {
        super(Arrays.asList(Component.SAMPLE_APP));
        this.commonSteps = commonSteps;
//        this.commonSteps.setJrs(() -> {
//            commonSteps.setJsonResponseString(jsonResponseString);
//            return "true";
//        });
        scenario = commonSteps.getScenario();
    }

    @When("Get users api called with page: {string} and size: {string}")
    public void getUsersApiCalledWithPagePageNoAndSizePageSize(String page, String size) {
        Map<String, String> query = Util.formQueryMap(String.format("?page=%s&per_page=%s", page, size));
        logger.debug("queryParam: " + query);
        response = sa.client.getUsers(query);
    }

    @Then("{string} api should respond with expectedStatusCode: {int}")
    public void getUsersApiShouldRespondWithExpectedStatusCodeExpectedStatusCode(String name, int expectedStatusCode) {
        logger.debug(name + " response code: " + response.getStatusCode() + " " + response.getStatusLine());
        response.then()
//                .log().all()
                .statusCode(expectedStatusCode);
        logger.trace(name + " response: " + response.body().asString());
    }

    @Then("Get users api should respond with total pages more than {int}")
    public void getUsersApiShouldRespondWithTotalPagesMoreThan(int count) {
        response.then().assertThat().body("page", greaterThanOrEqualTo(count));
    }

    @Then("Get users api should respond user id {int}")
    public void getUsersApiShouldRespondUserId(int id) {
        response.then().assertThat().body("data.id", hasItems(id));

        //TODO - get list of values
        ArrayList<Integer> expectedIds = new ArrayList<>();
        expectedIds.add(1);
        expectedIds.add(2);
        ArrayList<Integer> ids = response.then().extract().path("data.id");
        logger.debug("list" + ids);
        Assert.assertEquals(ids, expectedIds);

        //TODO - get list of pojos
        JsonPath jsonPath = response
                .then()
                .assertThat()
                .extract().body().jsonPath();

        List<User> users = jsonPath.getList("data", User.class);

        logger.debug("users: " + users);
    }

    @When("Get user api called with userId: {string}")
    public void getUserApiCalledWithUserIdUserId(String id) {
        response = sa.client.getUser(id);
    }

    @When("Post user api called with name: {string} and job: {string}")
    public void postUserApiCalledWithNameNameAndJobJob(String name, String job) {
        response = sa.client.createUser(name, job);
    }

    @Then("Post user api respond match the schema with name: {string} and job: {string}")
    public void postUserApiRespondMatchTheSchemaWithNameNameAndJobJob(String name, String job) {
        response.then().body(JsonSchemaValidator
                .matchesJsonSchema(new File("src/test/resources/com/ra/sa/schemas/createUser.json")))
                .body("name", equalTo(name))
                .body("job", equalTo(job));
    }

    @Then("Get user api response should match the schema")
    public void getUserApiRespondMatchTheSchema() {
        response.then().body(JsonSchemaValidator
                .matchesJsonSchema(new File("src/test/resources/com/ra/sa/schemas/getUser.json")));
    }

    @Then("Get users api response should match the schema")
    public void getUsersApiRespondMatchTheSchema() {
        response.then().body(JsonSchemaValidator
                .matchesJsonSchema(new File("src/test/resources/com/ra/sa/schemas/getUsers.json")));
    }

    @Then("Put user api respond match the schema with name: {string} and job: {string}")
    public void putUserApiRespondMatchTheSchemaWithNameNameAndJobJob(String name, String job) {
        response.then().body(JsonSchemaValidator
                .matchesJsonSchema(new File("src/test/resources/com/ra/sa/schemas/updateUser.json")))
                .body("name", equalTo(name))
                .body("job", equalTo(job));
    }

    @When("Put user api called to update user: {string} with name: {string} and job: {string}")
    public void putUserApiCalledToUpdateUserUserIdWithNameNameAndJobJob(String id, String name, String job) {
        response = sa.client.updateUser(id, name, job);
    }

    @When("Delete user api called to update user: {string}")
    public void deleteUserApiCalledToUpdateUserUserId(String id) {
        response = sa.client.deleteUser(id);
    }

    @Then("Delete user api should respond with expectedStatusCode: {int}")
    public void deleteUserApiShouldRespondWithExpectedStatusCodeExpectedStatusCode(int expectedStatusCode) {
        logger.debug("Delete user api response code: " + response.getStatusCode() + " " + response.getStatusLine());
        response.then()
//                .log().all()
                .statusCode(expectedStatusCode);
    }
}
