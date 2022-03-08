package com.ra.feign.clients;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

/**
 * This simple interface takes care of creating web security tokens
 *
 * @author Muthukumar Ramaiyah
 *
 */
public interface WebSecClient {

    @RequestLine("POST")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Response generateAuthToken(@Param("client_id") String clientId, @Param("client_secret") String clientSecret,
                      @Param("grant_type") String grantType, @Param("username") String username,
                      @Param("password") String password);

    @RequestLine("POST")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Response refreshToken(@Param("client_id") String clientId, @Param("client_secret") String clientSecret,
                               @Param("grant_type") String grantType, @Param("refresh_token") String refreshToken,
                               @Param("scope") String scope);

    @RequestLine("POST")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Response generateAuthToken(@Param("client_id") String clientId, @Param("client_secret") String clientSecret,
                               @Param("grant_type") String grantType);

    @RequestLine("POST")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Response generateAuthToken(@Param("client_id") String clientId, @Param("client_secret") String clientSecret,
                               @Param("grant_type") String grantType, @Param("scope") String scope);
}