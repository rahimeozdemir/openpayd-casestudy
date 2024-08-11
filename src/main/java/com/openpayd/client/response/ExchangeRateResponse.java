package com.openpayd.client.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExchangeRateResponse {
    private boolean success;
    private String source;
    private Map<String, Double> quotes;
}