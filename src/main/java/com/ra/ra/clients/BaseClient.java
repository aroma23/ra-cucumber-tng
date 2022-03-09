package com.ra.ra.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseClient {
    protected static RequestSpecification requestSpecification;

    protected BaseClient() {
        Logger logger = LogManager.getLogger(this.getClass());
        RACustomLogger raLogger = new RACustomLogger(logger);
        RestAssuredConfig restAssuredConfig = new RestAssuredConfig();
        LogConfig logConfig = restAssuredConfig.getLogConfig();
        logConfig.defaultStream(raLogger.getPrintStream()).enablePrettyPrinting(true);

        requestSpecification = new RequestSpecBuilder()
                .addFilter(ResponseLoggingFilter.logResponseTo(raLogger.getPrintStream()))
                .addFilter(RequestLoggingFilter.logRequestTo(raLogger.getPrintStream()))
                .setConfig(restAssuredConfig)
                .build();
    }
}
