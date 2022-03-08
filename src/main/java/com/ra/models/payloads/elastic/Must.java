
package com.ra.models.payloads.elastic;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Must {

    private Exists exists;

    public static Must preFill() {
        return Must.builder().exists(Exists.preFill()).build();
    }

    public static Must preFill(Exists exists) {
        return Must.builder().exists(exists).build();
    }

}
