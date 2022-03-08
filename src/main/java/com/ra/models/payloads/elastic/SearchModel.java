
package com.ra.models.payloads.elastic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ra.models.BaseModel;
import lombok.Builder;
import lombok.Data;
import org.json.JSONException;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
public class SearchModel extends BaseModel {

    @JsonProperty("_source")
    @Builder.Default
    private List<String> source = Arrays.asList("id", "elements", "alertType", "ttsElementValue", "fqdn", "division",
            "ticketNo", "ticketStatus", "eventStartTime", "indexTime");
    private Query query;
    @Builder.Default
    private Long size = 1L;

    public static SearchModel preFill() throws JSONException {
        return SearchModel.builder().query(Query.preFill()).build();
    }

    public static SearchModel preFill(Query query) {
        return SearchModel.builder().query(query).build();
    }

    public static SearchModel preFill(List<String> source) {
        return SearchModel.builder().source(source).build();
    }

    public static SearchModel preFill(Long size) {
        return SearchModel.builder().size(size).build();
    }

    public static SearchModel preFill(Long size, List<String> source) {
        return SearchModel.builder().size(size).source(source).build();
    }

    public static SearchModel preFill(Long size, List<String> source, Query query) {
        return SearchModel.builder().size(size).source(source).query(query).build();
    }
}
