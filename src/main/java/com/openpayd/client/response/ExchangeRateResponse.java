package com.openpayd.client.response;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Builder
@Jacksonized
@Data
public class ExchangeRateResponse {
    private Boolean success;
    private String source;
    private Map<String, Double> quotes;
}