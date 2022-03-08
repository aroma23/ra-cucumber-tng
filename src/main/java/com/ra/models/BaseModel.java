package com.ra.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This abstract class provides custom version of generating a json from POJO
 *
 * @author Muthukumar Ramaiyah
 *
 */
public abstract class BaseModel {

    protected static final String AQA_PREFIX = "AQAtest_";
    protected static final int ADDITIONAL_ALPHA_SYMBOLS_LENGTH = 6;

    public String toJsonString() {
        ObjectMapper mapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
