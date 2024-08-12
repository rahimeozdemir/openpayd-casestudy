package com.openpayd.client.response;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Data
public class ConvertCurrencyResponse {
    private Boolean success;
    private Double result;
    private Info info;

    @Jacksonized
    @Builder
    @Data
    public static class Info {
        private Long timestamp;
    }
}
