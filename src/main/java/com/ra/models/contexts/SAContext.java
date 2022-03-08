package com.ra.models.contexts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SAContext {
    @Builder.Default
    private String page="0";
    @Builder.Default
    private String pageSize="5";
    private String notes;
}
