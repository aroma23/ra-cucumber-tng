
package com.ra.models.payloads.elastic;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MustNot {

    private Exists exists;

    public static MustNot preFill() {
        return MustNot.builder().exists(Exists.preFill("elements.networkInterface")).build();
    }

    public static MustNot preFill(Exists exists) {
        return MustNot.builder().exists(exists).build();
    }
}
