package com.ra.components;

import com.ra.fw.BaseComponent;
import com.ra.fw.GenWebSec;
import com.ra.ra.clients.DPClient;
import org.json.JSONException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class DP extends BaseComponent {
    private static String staticToken = "";
    public final DPClient client;


    public DP(Properties properties) {
        //DP doesn't need any token for now
//        super("");
        super(properties);
        client = new DPClient(staticToken, properties);
    }

    @Override
    protected String generateToken(Properties properties) {
        return genTokenThreadSafe(() -> {
            if (staticToken.equals("")) {
                try {
                    staticToken = GenWebSec.getATFromFeign(properties.getProperty("web.sec.url"),
                            properties.getProperty("di.client.id"),
                            properties.getProperty("di.client.secret"),
                            properties.getProperty("di.grand.type"),
                            properties.getProperty("di.scope"));
                } catch (JSONException e) {
                    System.err.println(e.getMessage());
                    staticToken = "ERROR";
                }
            }
            return staticToken;
        });
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> T connectDB(String hostUrl, String userId, String password, String dbname) throws
            ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(hostUrl, userId, password);
        connection.setAutoCommit(false);
        System.out.println("Connection to postgres opened successfully");
        return (T) connection;
    }
}
