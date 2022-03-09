package com.ra.ra.clients;

import com.ra.models.payloads.sa.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

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
        return given()
                .queryParams(queryMap)
                .spec(spec)
//                .log().all()
                .get(baseUrl + "/api/users");
    }

    public Response getUser(String id) {
        return given()
                .spec(spec)
//                .log().all()
                .get(baseUrl + "/api/users/{id}", id);
    }

    public Response createUser(String name, String job) {
        return given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(User.preFill(name, job))
//                .log().all()
                .post(baseUrl + "/api/users").andReturn();
    }

    public Response updateUser(String id, String name, String job) {
        return given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(User.preFill(name, job))
//                .log().all()
                .put(baseUrl + "/api/users/{id}", id).andReturn();
    }

    public Response deleteUser(String id) {
        return given()
                .spec(spec)
                .contentType(ContentType.JSON)
//                .log().all()
                .delete(baseUrl + "/api/users/{id}", id).andReturn();
    }
}
