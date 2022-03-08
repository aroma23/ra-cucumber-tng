package com.ra.ra.clients;


import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
public class WebSecClient {

    WebSecClient () {}

    public static String getAccessToken(String webSecUrl, String clientId, String clientSecret, String grantType,
                                 String scope) {

        Response response = given()
                .formParam("client_id", clientId)
                .formParam("client_secret", clientSecret)
                .formParam("grant_type", grantType)
                .formParam("scope", scope)
//                .log().all()
                .post(webSecUrl).andReturn();
        response.then().statusCode(200).body("$", hasKey("access_token"));
        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        return jsonObject.getString("access_token");
    }
}
