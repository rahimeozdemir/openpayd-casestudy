package com.openpayd.strategy;

import com.openpayd.model.dto.ConvertCurrencyResponseDto;

public interface ExchangeRateStrategy {
    Double getExchangeRate(String fromCurrencyCode, String toCurrencyCode);

    ConvertCurrencyResponseDto convert(String fromCurrencyCode, String toCurrencyCode, Double amount);
}
