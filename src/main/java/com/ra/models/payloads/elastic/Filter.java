
package com.ra.models.payloads.elastic;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Filter {

    enum FilterType {TERM, RANGE}

    @Builder.Default
    private Map<String, String> term = new HashMap<>(Collections.singletonMap("alertType", "var_log_utilization_high"));

    @Builder.Default
    private Map<String, Map<String, String>> range = new HashMap<>(Collections.singletonMap("eventStartTime",
            Collections.singletonMap("gte", getTimeStamp(1))));

    public static Filter preFill() {
        return Filter.builder().range(null).build();
    }

    public static Filter preFill(Map<String, String> term) {
        return Filter.builder().term(term).build();
    }

    public static Filter preFill(FilterType ft) {
        if (ft.equals(FilterType.RANGE))
            return Filter.builder().term(null).build();
        else
            return preFill();
    }

    private static String getTimeStamp(int days) {
        return  Instant.now().minus(days, ChronoUnit.DAYS).toString().split("\\.")[0] + "Z";
    }

    public static Map<String, Map<String, String>> genRange(int days) {
        return new HashMap<>(Collections.singletonMap("eventStartTime", Collections.singletonMap("gte",
                getTimeStamp(days))));
    }
}
