package com.ra.fw;

import com.ra.components.SA;
import com.ra.enums.Component;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * This abstract class takes care of providing the things needed for a test suite
 *
 * @author Muthukumar Ramaiyah
 *
 */
public abstract class RAAbstractTest {
    protected static SA sa;
    protected static Logger logger;
    protected static Properties properties;
    protected Scenario scenario;

    private static volatile boolean areClientsInitialized = false;

    protected RAAbstractTest(List<Component> components) {
        logger = LogManager.getLogger(this.getClass());

        initializeProperties();

        components.forEach(RAAbstractTest::initializeComponent);
    }

    private static void checkSensitiveEmpty(String env) {
        boolean exitFlag = false;
        for (String sp: properties.getProperty("sensitive.list").trim().split("\\|"))
            if (!properties.containsKey(sp) || properties.getProperty(sp).trim().isEmpty()) {
                exitFlag = true;
                System.err.printf("Property: %s should be added in %s.properties (Don't push to git) " +
                        "or in mvn command with -D%s=<sensitivedata>%n", sp, env, sp);
            }
        if (exitFlag) System.exit(-1);
    }

    private static synchronized void initializeComponent(Component comp) {
        switch (comp) {
            case SAMPLE_APP:
                sa = initializeOnce(sa, SA.class);
                break;
        }
    }

    private static <T> T initializeOnce(T compInstance, Class<T> compClass) {
        try {
            if (compInstance == null)
                return compClass.getConstructor(Properties.class).newInstance(properties);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                IllegalAccessException ite) {
            logger.error(ite.getMessage());
        }
        return compInstance;
    }

    private static synchronized void initializeProperties() {
        if (!areClientsInitialized) {
            properties = new Properties();
            try {
                properties.load(Objects.requireNonNull(RAAbstractTest.class.getClassLoader().getResourceAsStream(
                        System.getProperty("test.env") + ".properties")));
                properties.putAll(System.getProperties());
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            //TODO - Update as per DA needs
            checkSensitiveEmpty(System.getProperty("test.env"));
            if (Boolean.parseBoolean(properties.getProperty("jenkins.retry.logic.enabled")))
                System.out.println("RETRY LOGIC IS ENABLED");
            areClientsInitialized = true;
        }
    }
}
