package com.ra.fw;

import com.ra.feign.clients.SAFeignBuilder;
import com.ra.feign.clients.WebSecClient;
import feign.Response;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class takes care of creating web security tokens - this is just an abstract and wrapper
 *
 * @author Muthukumar Ramaiyah
 *
 */
public class GenWebSec {
    GenWebSec() {}

    public static String getATFromFeign(String webSecUrl, String clientId, String clientSecret, String grantType,
                                           String userName, String password) throws JSONException {
        return getATFromFeignLocal(webSecUrl, clientId, clientSecret, grantType, userName, password);
    }

    public static String getATFromFeign(String webSecUrl, String clientId, String clientSecret, String grantType,
                                        String scope) throws JSONException {
        return getATFromFeignLocal(webSecUrl, clientId, clientSecret, grantType, scope, null, null);
    }

    public static String refreshTokenFromFeign(String webSecUrl, String clientId, String clientSecret, String grantType,
                                        String refreshToken, String scope) throws JSONException {
        Response response;
        WebSecClient webSecClient = SAFeignBuilder.buildWithFormEncoder(WebSecClient.class, webSecUrl);
        response = webSecClient.refreshToken(clientId, clientSecret, grantType, refreshToken, scope);
        return "Bearer " + new JSONObject(response.body().toString()).getString("id_token");
    }

    protected static String getATFromFeign(String webSecUrl, String clientId, String clientSecret, String grantType)
            throws JSONException {
        return getATFromFeignLocal(webSecUrl, clientId, clientSecret, grantType, null, null);
    }

    public static String getATFromFeign(String webSecUrl, String clientId, String clientSecret)
            throws JSONException {
        return getATFromFeignLocal(webSecUrl, clientId, clientSecret, "client_credentials",
                null, null);
    }

    private static String getATFromFeignLocal(String webSecUrl, String clientId, String clientSecret, String grantType,
                                              String scope, String userName, String password) throws JSONException {
        String accessToken;
        String key = "access_token";
        Response response;
        WebSecClient webSecClient = SAFeignBuilder.buildWithFormEncoder(WebSecClient.class, webSecUrl);
        if (grantType.equals("client_credentials")) {
            if (!scope.isEmpty())
                response = webSecClient.generateAuthToken(clientId, clientSecret, grantType, scope);
            else
                response = webSecClient.generateAuthToken(clientId, Util.decode(clientSecret), grantType);
        }
        else if (grantType.equals("password")) {
            response = webSecClient.generateAuthToken(clientId, clientSecret, grantType, userName,
                    Util.decode(password));
            key = "token";
        }
        else
            response = webSecClient.generateAuthToken(clientId, Util.decode(clientSecret), grantType,
                    userName, Util.decode(password));
        accessToken = new JSONObject(response.body().toString()).getString(key);
        return "Bearer " + accessToken;
    }


    private static String getATFromFeignLocal(String webSecUrl, String clientId, String clientSecret, String grantType,
                                           String userName, String password) throws JSONException {
        return getATFromFeignLocal(webSecUrl, clientId, clientSecret, grantType, null, userName, password);
    }
}
