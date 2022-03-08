
package com.ra.models.payloads.elastic;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Exists {

    @Builder.Default
    private String field="elements.ticketIdentifier";

    public static Exists preFill() {
        return Exists.builder().build();
    }

    public static Exists preFill(String fieldValue) {
        return Exists.builder().field(fieldValue).build();
    }

}
