package com.ra.feign.clients;

import feign.*;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import feign.gson.GsonDecoder;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.util.concurrent.TimeUnit;

/**
 * This simple class takes care of creating a instance of feign clients in this package
 *
 * @author Muthukumar Ramaiyah
 *
 */
public class SAFeignBuilder {
    private SAFeignBuilder() {

    }
    private static final String FEIGN_LOGGER_NAME = "feign";
    public static long timeoutSecs = 30;
    public static boolean retry = false;
    public static boolean sslTrustAll = false;
    public static Logger.Level logLevel = Logger.Level.FULL;
    public static String url = "";

    public static  <T> T build(Class<T> whichClass, String url) {
        return buildWithDecoder(whichClass, url);
    }

    public static  <T> T build(Class<T> whichClass, String url, Client client) {
        return buildWithDecoder(whichClass, url, client);
    }

    public static  <T> T build(Class<T> whichClass, Client client) {
        return buildWithDecoder(whichClass, url, client);
    }

    public static  <T> T build(Class<T> whichClass, String url, String loggerName) {
        return buildWithDecoder(loggerName, logLevel, whichClass, url);
    }
    public static  <T> T build(Class<T> whichClass, String url, Logger.Level logLevel) {
        return buildWithDecoder(FEIGN_LOGGER_NAME, logLevel, whichClass, url);
    }
    public static  <T> T build(Class<T> whichClass, String url, String loggerName, Logger.Level logLevel) {
        return buildWithDecoder(loggerName, logLevel, whichClass, url);
    }
    public static  <T> T buildWithFormEncoder(Class<T> whichClass, String url) {
        return buildWithEncoder(new FormEncoder(), whichClass, url);
    }
    private static Retryer getRetryer(boolean retry) {
        return retry ?  new Retryer.Default() :  Retryer.NEVER_RETRY;
    }

    private static <T> T buildWithDecoder(Class<T> whichClass, String url) {
        return build(FEIGN_LOGGER_NAME, logLevel).decoder(new GsonDecoder()).target(whichClass, url);
    }

    private static <T> T buildWithDecoder(Class<T> whichClass, String url, Client client) {
        return build(FEIGN_LOGGER_NAME, logLevel, client).decoder(new GsonDecoder())
                .target(whichClass, url);
    }

    private static <T> T buildWithDecoder(String loggerName, Logger.Level logLevel, Class<T> whichClass, String url) {
        return build(loggerName, logLevel).decoder(new GsonDecoder()).target(whichClass, url);
    }

    private static <T> T buildWithEncoder(Encoder encoder, Class<T> whichClass,
                                       String url) {
        return build(FEIGN_LOGGER_NAME, logLevel).encoder(encoder).target(whichClass, url);
    }

    //https://www.thetopsites.net/article/53258234.shtml
    private static Feign.Builder build(String loggerName, Logger.Level logLevel) {
        Feign.Builder temp = Feign.builder().retryer(getRetryer(retry)).logger(new Slf4jLogger(loggerName)).logLevel(logLevel)
                .options(new Request.Options(timeoutSecs, TimeUnit.SECONDS, timeoutSecs, TimeUnit.SECONDS,
                        true));
        if (sslTrustAll) temp.client(new Client.Default(getSSLSocketFactory(), new NoopHostnameVerifier()));
        return temp;
    }

    private static Feign.Builder build(String loggerName, Logger.Level logLevel, Client client) {
        return build(loggerName, logLevel).client(client);
    }

    private static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null,
                    new TrustSelfSignedStrategy()).build();
            return sslContext.getSocketFactory();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
