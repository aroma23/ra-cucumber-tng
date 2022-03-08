
package com.ra.models.payloads.elastic;

import lombok.Builder;
import lombok.Data;
import org.json.JSONException;

@Data
@Builder
public class Query {

    private Bool bool;

    public static Query preFill() throws JSONException {
        return Query.builder().bool(Bool.preFill()).build();
    }

    public static Query preFill(Bool bool) {
        return Query.builder().bool(bool).build();
    }
}
