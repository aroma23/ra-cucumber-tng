package com.ra.models.payloads.sa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ra.models.BaseModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseModel {
    private String name;
    private String job;

    public static User preFill(String name, String job) {
        return User.builder().name(name).job(job).build();
    }

    public static User getPOJO(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonString, User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return User.preFill("dummy", "dummy");
    }
}
