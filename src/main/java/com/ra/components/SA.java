package com.ra.components;

import com.ra.fw.BaseComponent;
import com.ra.fw.GenWebSec;
import com.ra.ra.clients.SAClient;
import org.json.JSONException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

//Device Inventory or Device Portal
public class SA extends BaseComponent {
    private static String staticToken = "";
    public final SAClient client;

    public List<String> otdrMappingSchema = Arrays.asList("physicalConnectionId", "device", "id", "resource",
            "otdrSupported");
    public List<String> otdrMappingCircuitInvSchema = Arrays.asList("routerCircuit", "routerA", "id",
            "physicalInterfaceA", "routerB", "physicalInterfaceB", "transportSource", "transportDest",
            "fiberpathForward", "fiberpathReverse");

    public SA(Properties properties) {
        //Device Inventory doesn't need any token for now
        super("");
//        super(properties);
        client = new SAClient(staticToken, properties);
    }

    @Override
    protected String generateToken(Properties properties) {
        return genTokenThreadSafe(() -> {
            if (staticToken.equals("")) {
                try {
                    staticToken = GenWebSec.getATFromFeign(properties.getProperty("web.sec.url"),
                            properties.getProperty("sa.client.id"),
                            properties.getProperty("sa.client.secret"),
                            properties.getProperty("sa.grand.type"),
                            properties.getProperty("sa.scope"));
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
