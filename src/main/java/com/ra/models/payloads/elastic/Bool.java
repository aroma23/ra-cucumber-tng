
package com.ra.models.payloads.elastic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.json.JSONException;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Bool {

    private List<Filter> filter;
    private List<Must> must;
    @JsonProperty("must_not")
    private List<MustNot> mustNot;
    private List<Object> should;

    public static Bool preFill() throws JSONException {
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.preFill());
        filters.add(Filter.preFill(Filter.FilterType.RANGE));
        return Bool.builder()
                .must(Collections.singletonList(Must.preFill()))
                .filter(filters)
                .mustNot(Collections.singletonList(MustNot.preFill()))
                .build();
    }
}
