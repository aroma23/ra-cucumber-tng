package com.ra.ra.clients;

import com.ra.models.payloads.sa.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

//Sample Application from reqres.in
public class SAClient extends BaseClient{
    private String accessToken;
    private Properties properties;
    private String baseUrl;

    public SAClient(String at, String baseUrl) {
        this.accessToken = at;
        this.baseUrl = baseUrl;
    }

    public SAClient(String at, Properties properties) {
        this.accessToken = at;
        this.properties = properties;
        this.baseUrl = this.properties.getProperty("sa.url");
    }

    public Response getUsers(Map<String, String> queryMap) {
        return getReqS()
                .queryParams(queryMap)
                .get(baseUrl + "/api/users");
    }

    public Response getUser(String id) {
        return getReqS()
                .get(baseUrl + "/api/users/{id}", id);
    }

    public Response createUser(String name, String job) {
        return getReqS()
                .body(User.preFill(name, job))
                .post(baseUrl + "/api/users").andReturn();
    }

    public Response updateUser(String id, String name, String job) {
        return getReqS()
                .body(User.preFill(name, job))
                .put(baseUrl + "/api/users/{id}", id).andReturn();
    }

    public Response deleteUser(String id) {
        return getReqS()
                .delete(baseUrl + "/api/users/{id}", id).andReturn();
    }

    private RequestSpecification getReqS() {
        return given().spec(requestSpecification).contentType(ContentType.JSON);
    }
}
