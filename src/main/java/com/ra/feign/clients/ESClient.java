package com.ra.feign.clients;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import org.json.JSONObject;

import java.util.Properties;

/**
 * This simple interface takes care of calling ES - Elastic Search
 *
 * @author Muthukumar Ramaiyah
 *
 */
public interface ESClient {

//    @RequestLine("POST /doc/_delete_by_query")
//    @Headers({"Content-Type: application/json"})
//    Response deleteByQuery(String body);
//
    @RequestLine("GET /alerts/_search")
    @Headers({"Content-Type: application/json"})
    Response alertSearchByQuery(String body);

    @RequestLine("GET /_slm/policy/nightly-snapshots?human&pretty")
    @Headers({"Content-Type: application/json"})
    Response getS3BKPShowPolicy();

    @RequestLine("POST /{index}/_doc/{id}")
    @Headers({"Content-Type: application/json"})
    Response createDocument(@Param("index") String index, @Param("id") String id, String body);

}