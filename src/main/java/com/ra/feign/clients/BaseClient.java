package com.ra.feign.clients;

import feign.RequestLine;
import feign.Response;

/**
 * This simple interface takes care of calling alert service API
 *
 * @author Muthukumar Ramaiyah
 */

public interface BaseClient {

    @RequestLine("GET /endpoints/health")
    Response getHealth();
}
