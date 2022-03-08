package com.ra.ra.clients;

import io.restassured.response.Response;

import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class DPClient {
    private String accessToken;
    private Properties properties;
    private String baseUrl;

    public DPClient(String at, String baseUrl) {
        this.accessToken = at;
        this.baseUrl = baseUrl;
    }

    public DPClient(String at, Properties properties) {
        this.accessToken = at;
        this.properties = properties;
        this.baseUrl = this.properties.getProperty("di.url");
    }

    public Response getDecommissionDevices(Map<String, String> queryMap) {
        return given()
                .queryParams(queryMap)
                .get(baseUrl + "/api/v2/devices/decommission");
    }

}
