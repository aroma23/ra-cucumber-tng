package com.ra.fw;

import com.ra.ra.clients.WebSecClient;
import org.json.JSONException;

import java.sql.SQLException;
import java.util.Properties;
import java.util.function.Supplier;

public abstract class BaseComponent {

    private static volatile String staticToken = "";
    public final String bearerToken;

    protected BaseComponent(Properties properties) {
        bearerToken = generateToken(properties);
    }

    protected BaseComponent(String token) {
        bearerToken = token;
    }

    protected String generateToken(Properties properties) {
        return genTokenThreadSafe(() -> {
            if (staticToken.equals("")) {
                try {
                    staticToken = WebSecClient.getAccessToken(properties.getProperty("web.sec"),
                            properties.getProperty("client.id"),
                            properties.getProperty("client.secret"),
                            properties.getProperty("grand.type"),
                            properties.getProperty("scope"));

                } catch (JSONException e) {
                    System.err.println(e.getMessage());
                    staticToken = "ERROR";
                }
            }
            return staticToken;
        });
    }

    protected static synchronized String genTokenThreadSafe(Supplier<String> func) {
        return func.get();
    }
    protected abstract <T> T connectDB(String hostUrl, String userId, String password, String dbname) throws ClassNotFoundException, SQLException;
}
